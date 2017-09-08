package co.jp.nej.earth.error;

import co.jp.nej.earth.exception.ConnectionWorkSpaceException;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.util.EMessageResource;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * General error handler for the application.
 */
@ControllerAdvice
class ExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandler.class);

    @Autowired
    private EMessageResource eMessageResource;

    /**
     * Handle exceptions thrown by handlers.
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public ModelAndView exception(Exception exception, WebRequest request) {
        LOG.error(exception.getMessage(), exception);
        ModelAndView modelAndView = new ModelAndView("error/error");
        List<Message> messages = new ArrayList<>();

        // Get error of connection workspace
        if (exception instanceof ConnectionWorkSpaceException) {
            ConnectionWorkSpaceException workSpaceException = (ConnectionWorkSpaceException)exception;
            if (Constant.ErrorCode.E0009.equals(workSpaceException.getErrorCode())) {
                String[] params = { eMessageResource.get(Constant.ScreenItem.WORKSPACE) };
                messages.add(new Message(Constant.ErrorCode.E0009,
                    eMessageResource.get(Constant.ErrorCode.E0009, params)));
            } else {
                messages.add(new Message(Constant.ErrorCode.E0009,
                    eMessageResource.get(Constant.ErrorCode.E0009, null)));
            }
        }
        modelAndView.addObject("messages", messages);
        modelAndView.addObject("errorMessage", Throwables.getRootCause(exception));
        return modelAndView;
    }
}
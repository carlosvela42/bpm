package co.jp.nej.earth.web.restcontroller;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.session.EarthSessionManager;
import co.jp.nej.earth.model.Document;
import co.jp.nej.earth.model.constant.Constant.ErrorCode;
import co.jp.nej.earth.model.ws.CloseImageRequest;
import co.jp.nej.earth.model.ws.DisplayImageRequest;
import co.jp.nej.earth.model.ws.DisplayImageResponse;
import co.jp.nej.earth.model.ws.GetDocumentRequest;
import co.jp.nej.earth.model.ws.GetDocumentResponse;
import co.jp.nej.earth.model.ws.GetThumbernailResponse;
import co.jp.nej.earth.model.ws.Response;
import co.jp.nej.earth.model.ws.SaveAndCloseImagesRequest;
import co.jp.nej.earth.model.ws.SaveImageRequest;
import co.jp.nej.earth.model.ws.UpdateDocumentRequest;
import co.jp.nej.earth.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/WS")
public class DocumentRestController extends BaseRestController {

    @Autowired
    private DocumentService documentService;

    @RequestMapping(value = "/getDocument", method = RequestMethod.GET)
    public Response getDocument(@Valid GetDocumentRequest request, BindingResult bindingResult)
            throws EarthException {
        return getRestResponse(request, bindingResult, () -> {
            Document doc = documentService.getDocumentSession(EarthSessionManager.find(request.getToken()),
                    request.getWorkspaceId(), request.getWorkItemId(), request.getFolderItemNo(),
                    request.getDocumentNo());
            if (doc == null) {
                return new GetDocumentResponse(messageSource.get(ErrorCode.E0029, new String[] { "document" }));
            }
            return new GetDocumentResponse(true, doc);
        });

    }

    @RequestMapping(value = "/updateDocument", method = RequestMethod.POST)
    public Response updateDocument(@Valid @RequestBody UpdateDocumentRequest request, BindingResult bindingResult)
            throws EarthException {
        return getRestResponse(request, bindingResult, () -> {
            boolean result = documentService.updateDocumentSession(EarthSessionManager.find(request.getToken()),
                    request.getWorkspaceId(), request.getWorkItemId(), request.getFolderItemNo(),
                    request.getDocument());
            return new Response(result);
        });
    }

    @RequestMapping(value = "/getImagesByFolderItem", method = RequestMethod.GET)
    public Response getImagesByFolderItem(@Valid DisplayImageRequest request, BindingResult bindingResult) {
        return getRestResponse(request, bindingResult, () -> {

            DisplayImageResponse displayImageResponse = documentService.getImagesByFolderItem(
                    EarthSessionManager.find(request.getToken()), request.getWorkspaceId(), request.getWorkItemId(),
                    request.getFolderItemNo(), request.getDocumentNo());
            if (displayImageResponse.getDocumentMap().size() == 0) {
                return new GetDocumentResponse(messageSource.get(ErrorCode.E0029, new String[] { "document" }));
            }

            displayImageResponse.setResult(true);
            return displayImageResponse;
        });
    }

    @RequestMapping(value = "/saveImage", method = RequestMethod.POST)
    public Response saveImage(@Valid @RequestBody SaveImageRequest request, BindingResult bindingResult)
            throws EarthException {
        return getRestResponse(request, bindingResult, () -> {
            boolean result = documentService.saveImageSession(EarthSessionManager.find(request.getToken()),
                    request.getWorkspaceId(), request.getDocument());
            return new Response(result);
        });
    }

    @RequestMapping(value = "/closeImage", method = RequestMethod.POST)
    public Response closeImage(@Valid @RequestBody CloseImageRequest request, BindingResult bindingResult)
            throws EarthException {
        return getRestResponse(request, bindingResult, () -> {
            boolean result = documentService.closeImage(EarthSessionManager.find(request.getToken()),
                    request.getWorkspaceId(), request.getWorkItemId(), request.getFolderItemNo(),
                    request.getDocumentNo());
            return new Response(result);
        });
    }

    @RequestMapping(value = "/saveAndCloseImageViewer", method = RequestMethod.POST)
    public Response saveAndCloseImageViewer(@Valid @RequestBody SaveAndCloseImagesRequest request, BindingResult
        bindingResult)
            throws EarthException {
        return getRestResponse(request, bindingResult, () -> {
            boolean result = documentService.saveAndCloseImageViewer(EarthSessionManager.find(request.getToken()),
                    request.getWorkspaceId(), request.getWorkItemId(), request.getFolderItemNo());
            return new Response(result);
        });
    }

    @RequestMapping(value = "/closeImageViewer", method = RequestMethod.POST)
    public Response closeImageViewer(@Valid @RequestBody CloseImageRequest request, BindingResult bindingResult)
            throws EarthException {
        return getRestResponse(request, bindingResult, () -> {
            boolean result = documentService.closeImageViewer(EarthSessionManager.find(request.getToken()),
                    request.getWorkspaceId(), request.getWorkItemId(), request.getFolderItemNo());
            return new Response(result);
        });
    }

    @RequestMapping(value = "/getAnnotationsByDocument", method = RequestMethod.GET)
    public Response getAnnotationsByDocument(HttpServletResponse response, @Valid GetDocumentRequest request,
            BindingResult bindingResult) throws EarthException {
        return getRestResponse(request, bindingResult, () -> {
            String thumbernail = documentService
                .getAnnotationsByDocument(EarthSessionManager.find(request.getToken()),
                                    request.getWorkspaceId(), request.getWorkItemId(), request.getFolderItemNo(),
                                    request.getDocumentNo());

            return new GetThumbernailResponse(true, thumbernail);
        });
    }

    @RequestMapping(value = "/getDocumentBinary", method = RequestMethod.GET)
    public void getDocumentBinary(@Valid GetDocumentRequest request, BindingResult bindingResult,
            HttpServletResponse response) throws EarthException {
        getRestResponse(request, bindingResult, () -> {
            byte[] binary = documentService.getBinaryDataOfDocument(request.getWorkspaceId(),
                    documentService.getDocumentSession(EarthSessionManager.find(request.getToken()),
                            request.getWorkspaceId(), request.getWorkItemId(), request.getFolderItemNo(),
                            request.getDocumentNo()));
            if (binary == null) {
                return new Response(messageSource.get(ErrorCode.E0029, new String[] { "data" }));
            }

            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(binary);
                org.apache.commons.io.IOUtils.copy(bis, response.getOutputStream());
                response.flushBuffer();
            } catch (IOException e) {
                throw new EarthException(e);
            }

            return new Response(true);
        });

    }
}

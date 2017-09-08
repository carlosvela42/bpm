package co.jp.nej.earth.model.ws;

import org.hibernate.validator.constraints.NotEmpty;

public class UpdateSystemDateRequest extends Request {

    @NotEmpty(message = ("E001,date.input"))
    private String dateInput;

    /**
     * @return the dateInput
     */
    public String getDateInput() {
        return dateInput;
    }

    /**
     * @param dateInput the dateInput to set
     */
    public void setDateInput(String dateInput) {
        this.dateInput = dateInput;
    }

}

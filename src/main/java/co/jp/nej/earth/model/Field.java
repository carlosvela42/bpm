package co.jp.nej.earth.model;

public class Field {
    private String name;
    private String description;
    private Integer type;
    private Integer size;
    private Boolean required = false;
    private Boolean encrypted = false;

    /**
     *
     */
    public Field() {
        super();
    }

    /**
     * @param name
     * @param description
     * @param type
     * @param size
     * @param required
     * @param encrypted
     */
    public Field(String name, String description, Integer type, Integer size, Boolean required, Boolean encrypted) {
        super();
        this.name = name;
        this.description = description;
        this.type = type;
        this.size = size;
        this.required = required;
        this.encrypted = encrypted;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return the size
     */
    public Integer getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Integer size) {
        this.size = size;
    }

    /**
     * @return the required
     */
    public Boolean getRequired() {
        return required;
    }

    /**
     * @param required the required to set
     */
    public void setRequired(Boolean required) {
        this.required = required;
    }

    /**
     * @return the encrypted
     */
    public Boolean getEncrypted() {
        return encrypted;
    }

    public Boolean isEncrypted() {
        return (encrypted!= null && encrypted.booleanValue());
    }

    /**
     * @param encrypted the encrypted to set
     */
    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
    }
}

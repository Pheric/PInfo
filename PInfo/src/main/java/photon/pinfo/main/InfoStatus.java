package photon.pinfo.main;

public enum InfoStatus {
    NORMAL("normal"),
    ERROR("err");
    private String value;
    private InfoStatus(String value){
        this.value=value;
    }
    public String getValue(){
        return value;
    }
    @Override
    public String toString() {
        return getValue();
    }
}

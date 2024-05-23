package info;

public interface RegUsers {

    boolean register (RegForm form);
    boolean cancel (String id);
    RegForm getByID(String id);

}

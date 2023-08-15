package sg.edu.nus.iss.adprojectbackend.auth;

public interface ResponseModel<T> {
    T getData();
    String getMessage();
}

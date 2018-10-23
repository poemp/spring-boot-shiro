package org.poem.result;

/**
 * @author poem
 */
public class ResultVO<T> {

    private Integer status;

    private T data;

    private String message;

    private ResultVO(){
        this.status = 0;
    }

    public ResultVO(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResultVO(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    public ResultVO(Integer status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResultVO{" +
                "status=" + status +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}

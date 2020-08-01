package testproject.yara.batman.data.datasource.remotedatasource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import testproject.yara.batman.util.enums.Status;

import static testproject.yara.batman.util.enums.Status.ERROR;
import static testproject.yara.batman.util.enums.Status.LOADING;
import static testproject.yara.batman.util.enums.Status.SUCCESS;

/**
 * A generic class that holds a value with its loading status.
 *
 * @param <T> type of data
 */
public class Resource<T> {
    @NonNull
    private final Status status;
    @Nullable
    private final T data;
    @Nullable
    private final String message;

    @NonNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    private Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resource)) return false;

        Resource<?> resource = (Resource<?>) o;

        if (status != resource.status) return false;
        if (data != null ? !data.equals(resource.data) : resource.data != null) return false;
        return message != null ? message.equals(resource.message) : resource.message == null;
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
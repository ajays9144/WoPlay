package com.world.play.ui.feature.base;

import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.world.play.di.UtilsModule;
import com.world.play.models.StandardResponse;

import java.lang.annotation.Annotation;
import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;
import retrofit2.Response;

import static com.world.play.AppController.API_STATUS_CODE_LOCAL_ERROR;


public class BaseViewModel<V extends Contract.View> extends ViewModel {
    public CompositeDisposable compositeDisposable;

    private V view;

    public final V getView() {
        return this.view;
    }

    public void setView(V view) {
        this.view = view;
    }

    public final CompositeDisposable getCompositeDisposable() {
        CompositeDisposable compositeDisposable2 = this.compositeDisposable;
        if (compositeDisposable2 == null) {
            Log.e(BaseViewModel.class.getSimpleName(), "missing");
        }
        return compositeDisposable2;
    }

    public void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    public final void attachView(V v) {
        this.view = v;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        CompositeDisposable compositeDisposable2 = this.compositeDisposable;
        if (compositeDisposable2 == null) {
            Log.e(BaseViewModel.class.getSimpleName(), "missing");
        }
        compositeDisposable2.clear();
        this.view = null;
    }

    public StandardResponse parseError(Throwable e) {
        try {
            return parseError(((HttpException) e).response());
        } catch (ClassCastException e1) {
            return handleApiError(e);
        }
    }

    public StandardResponse parseError(Response<?> response) {

        UtilsModule utilsModule = new UtilsModule();
        Converter<ResponseBody, StandardResponse> converter = utilsModule.provideRetrofit(utilsModule.provideGson(),
                utilsModule.getRequestHeader()).responseBodyConverter(StandardResponse.class, new Annotation[0]);
        StandardResponse error;
        try {
            error = converter.convert(response.errorBody());
        } catch (Exception e) {
            return StandardResponse.EMPTY_VIEW;
        }
        return error;

    }

    public StandardResponse handleApiError(Throwable error) {
        if (error instanceof UnknownHostException) {
            return StandardResponse.NO_INTERNET_CONNECTION;
        } else if (error instanceof HttpException) {
            switch (((HttpException) error).code()) {
                case HttpsURLConnection.HTTP_UNAUTHORIZED:
                    return StandardResponse.UNAUTH_USER;
                case HttpsURLConnection.HTTP_FORBIDDEN:
                    return StandardResponse.FORBIDDEN_USER;
                case HttpsURLConnection.HTTP_INTERNAL_ERROR:
                    return StandardResponse.INTERNAL_SERVER;
                case HttpsURLConnection.HTTP_BAD_REQUEST:
                    return StandardResponse.BAD_REQUEST;
                case API_STATUS_CODE_LOCAL_ERROR:
                    return StandardResponse.NO_INTERNET_CONNECTION;
                default:
                    return StandardResponse.EMPTY_VIEW;

            }
        }
        return StandardResponse.EMPTY_VIEW;
    }
}

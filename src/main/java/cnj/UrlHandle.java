package cnj;

import cnj.Exception.UrlNotMatchException;

public interface UrlHandle {
    Response handleURL(Request request,String url) throws UrlNotMatchException;
}

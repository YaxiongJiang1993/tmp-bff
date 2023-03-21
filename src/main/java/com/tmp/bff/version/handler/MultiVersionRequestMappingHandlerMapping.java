package com.tmp.bff.version.handler;

import com.tmp.bff.version.annotation.ApiVersion;
import com.tmp.bff.version.discover.ApiVersionDiscover;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.tmp.bff.version.util.VersionParseUtil.getVersionNum;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;


/**
 * 多版本处理器
 *
 * @author jiangyaxiong
 */
@Slf4j
public class MultiVersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private final Map<MultiVersionRequest, List<MultiVersionRequestHandler>> MULTI_HANDLER_METHOD_MAP = new HashMap<>();

    private final List<ApiVersionDiscover> apiVersionDiscovers = new ArrayList<>();

    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {
        ApiVersion apiVersion = method.getAnnotation(ApiVersion.class);
        if (nonNull(apiVersion)) {
            registerMultiVersionHandlerMethod(handler, method, mapping, apiVersion);
            return;
        }
        super.registerHandlerMethod(handler, method, mapping);
    }

    @Override
    protected HandlerMethod lookupHandlerMethod(String lookupPath, HttpServletRequest request) throws Exception {
        HandlerMethod handlerMethod = lookupMultiVersionHandlerMethod(lookupPath, request);
        if (nonNull(handlerMethod)) {
            return handlerMethod;
        }
        return super.lookupHandlerMethod(lookupPath, request);
    }

    public void registerApiVersionCodeDiscovers(ApiVersionDiscover apiVersionDiscover) {
        if (!apiVersionDiscovers.contains(apiVersionDiscover)) {
            apiVersionDiscovers.add(apiVersionDiscover);
        }
    }

    private void registerMultiVersionHandlerMethod(Object handler,
                                                   Method method,
                                                   RequestMappingInfo mapping,
                                                   ApiVersion apiVersion) {
        PatternsRequestCondition patternsCondition = mapping.getPatternsCondition();
        RequestMethodsRequestCondition methodsRequestCondition = mapping.getMethodsCondition();
        if (isNull(patternsCondition)
                || isNull(methodsRequestCondition)
                || patternsCondition.getPatterns().size() == 0
                || methodsRequestCondition.getMethods().size() == 0) {
            return;
        }
        Iterator<String> patternIterator = patternsCondition.getPatterns().iterator();
        Iterator<RequestMethod> methodsIterator = methodsRequestCondition.getMethods().iterator();
        while (patternIterator.hasNext() && methodsIterator.hasNext()) {
            String patternItem = patternIterator.next();
            RequestMethod methodItem = methodsIterator.next();
            registerHandleMethod(handler, method, patternItem, methodItem, apiVersion);
        }
    }

    /**
     * 将handler注册到handler map中
     *
     * @param handler     handler
     * @param method      method
     * @param patternItem patternItem
     * @param methodItem  methodItem
     * @param apiVersion  apiVersion
     */
    private void registerHandleMethod(Object handler,
                                      Method method,
                                      String patternItem,
                                      RequestMethod methodItem,
                                      ApiVersion apiVersion) {

        int minVersion = getVersionNum(apiVersion.minVersion());
        if (minVersion < 0) {
            throw new BeanCreationException("multiVersionRequestMappingHandlerMapping",
                    "minVersion parse error, minVersion: " + minVersion);
        }
        HandlerMethod handlerMethod = super.createHandlerMethod(handler, method);
        MultiVersionRequest multiVersionRequest = MultiVersionRequest.builder()
                .path(patternItem)
                .method(methodItem.name())
                .build();
        MultiVersionRequestHandler multiVersionRequestHandler = MultiVersionRequestHandler.builder()
                .minVersion(minVersion)
                .handlerMethod(handlerMethod)
                .build();
        List<MultiVersionRequestHandler> multiVersionRequestHandlers = MULTI_HANDLER_METHOD_MAP
                .computeIfAbsent(multiVersionRequest, k -> new ArrayList<>());
        if (!multiVersionRequestHandlers.contains(multiVersionRequestHandler)) {
            multiVersionRequestHandlers.add(multiVersionRequestHandler);
            multiVersionRequestHandlers.sort(Comparator.comparing(MultiVersionRequestHandler::getMinVersion));
            log.debug("register api version handler method, key: {}, method: {}",
                    multiVersionRequest, multiVersionRequestHandlers);
        }
    }

    private HandlerMethod lookupMultiVersionHandlerMethod(String lookupPath, HttpServletRequest request) {
        int versionCode = tryResolveApiVersion(request);
        if (versionCode < 0) {
            if (-2 == versionCode) {
                log.error("lookupMultiVersionHandlerMethod tryResolveApiVersion failed, versionCode: {}", versionCode);
            }
            return null;
        }
        MultiVersionRequest multiVersionRequest = MultiVersionRequest.builder()
                .path(lookupPath)
                .method(request.getMethod())
                .build();
        HandlerMethod handlerMethod = searchHandlerMethod(multiVersionRequest, versionCode);
        if (isNull(handlerMethod)) {
            log.debug("lookup api version handler, multiVersionRequest: {}, failed", multiVersionRequest);
            return null;
        }
        log.debug("lookuped api version handler, multiVersionRequest: {}, handler: {}",
                multiVersionRequest, handlerMethod);
        return handlerMethod;
    }


    private int tryResolveApiVersion(HttpServletRequest request) {
        return apiVersionDiscovers.stream()
                .map(e -> e.getVersionCode(request))
                .filter(e -> e >= 0)
                .findFirst()
                .orElse(-1);
    }

    private HandlerMethod searchHandlerMethod(MultiVersionRequest multiVersionRequest, int versionCode) {

        List<MultiVersionRequestHandler> handlers = MULTI_HANDLER_METHOD_MAP
                .getOrDefault(multiVersionRequest, new ArrayList<>());
        if (isNull(handlers) || handlers.size() == 0) {
            return null;
        }
        int low = 0, high = handlers.size() - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            MultiVersionRequestHandler handler = handlers.get(mid);
            int minVersion = handler.getMinVersion();
            int maxVersion = 0;
            if (mid == handlers.size() - 1) {
                maxVersion = Integer.MAX_VALUE;
            } else {
                maxVersion = handlers.get(mid + 1).getMinVersion();
            }
            if (minVersion <= versionCode && versionCode < maxVersion) {
                return handler.getHandlerMethod();
            } else if (versionCode < handler.getMinVersion()) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return null;
    }
}

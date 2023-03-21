package com.tmp.bff.service.home;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.tmp.bff.version.util.VersionParseUtil.getVersionNum;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.groupingBy;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

/**
 * HomeModuleHandler
 *
 * @author jiangyaxiong
 */
@Component
public class HomeModuleHandlerFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private Map<Byte, List<HomeModuleHandlerWrapper>> handlerMap = Maps.newHashMap();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        Map<String, Object> handlerObjectMap = applicationContext.getBeansWithAnnotation(ModuleHandlerVersion.class);
        handlerMap = handlerObjectMap.values()
                .stream()
                .map(this::wrapHandler)
                .filter(Objects::nonNull)
                .filter(HomeModuleHandlerWrapper::getRegister)
                .collect(groupingBy(HomeModuleHandlerWrapper::getType));
        handlerMap.forEach((k, v) -> v.sort(Comparator.comparingInt(HomeModuleHandlerWrapper::getVersionNum)));
    }

    private HomeModuleHandlerWrapper wrapHandler(Object object) {
        if (!(object instanceof HomeModuleHandler)) {
            return null;
        }
        HomeModuleHandler handler = (HomeModuleHandler) object;
        ModuleHandlerVersion handlerVersion = handler.getClass().getAnnotation(ModuleHandlerVersion.class);
        int version = getVersionNum(handlerVersion.minVersion());
        if (version < 0) {
            throw new BeanCreationException(handler.toString(),
                    "handler parse error, version: " + handlerVersion.minVersion());
        }
        return HomeModuleHandlerWrapper.builder()
                .handler(handler)
                .minVersion(handlerVersion.minVersion())
                .versionNum(version)
                .type(handlerVersion.type())
                .register(handlerVersion.register())
                .build();
    }

    public HomeModuleHandler getHandler(Byte type, String version) {
        List<HomeModuleHandlerWrapper> handlerWrappers = handlerMap.get(type);
        if (isEmpty(handlerWrappers)) {
            return null;
        }
        int versionNum = getVersionNum(version);
        HomeModuleHandlerWrapper handlerWrapper = searchHandler(handlerWrappers, versionNum);
        return nonNull(handlerWrapper) ? handlerWrapper.getHandler() : null;
    }

    private HomeModuleHandlerWrapper searchHandler(List<HomeModuleHandlerWrapper> handlerWrappers, int versionNum) {
        if (isEmpty(handlerWrappers)) {
            return null;
        }
        int low = 0, high = handlerWrappers.size() - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            HomeModuleHandlerWrapper handlerWrapper = handlerWrappers.get(mid);
            int minVersion = handlerWrapper.getVersionNum();
            int maxVersion = 0;
            if (mid == handlerWrappers.size() - 1) {
                maxVersion = Integer.MAX_VALUE;
            } else {
                maxVersion = handlerWrappers.get(mid + 1).getVersionNum();
            }
            if (minVersion <= versionNum && versionNum < maxVersion) {
                return handlerWrapper;
            } else if (minVersion > versionNum) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return null;
    }

    @Data
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HomeModuleHandlerWrapper {

        private HomeModuleHandler handler;

        private String minVersion;

        private Integer versionNum;

        private Byte type;

        private Boolean register;

    }
}

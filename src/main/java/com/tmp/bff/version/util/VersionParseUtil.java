package com.tmp.bff.version.util;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * 版本解析工具
 *
 * @author jiangyaxiong
 */
public class VersionParseUtil {

    /**
     * 判断version是否满足minVersion maxVersion 的条件
     *
     * @param minVersion minVersion
     * @param maxVersion maxVersion
     * @param version    version
     * @return boolean
     */
    public static boolean matchVersion(String minVersion, String maxVersion, String version) {
        if (isBlank(minVersion) && isBlank(maxVersion)) {
            return true;
        }
        if (isBlank(version)) {
            return false;
        }
        boolean minResult = isBlank(minVersion) ||
                getVersionNum(version) >= getVersionNum(minVersion);

        boolean maxResult = isBlank(maxVersion) ||
                getVersionNum(version) <= getVersionNum(maxVersion);
        return minResult && maxResult;
    }

    public static int getVersionNum(String version) {
        if (isBlank(version)) {
            return -1;
        }
        List<Integer> versions = getVersions(version);
        if (versions.size() < 3) {
            return -2;
        }
        return versions.get(0) * 1000000 + versions.get(1) * 1000 + versions.get(2);
    }

    public static List<Integer> getVersions(String version) {
        if (isBlank(version)) {
            return Lists.newArrayList();
        }
        return Lists.newArrayList(
                Splitter.on(".")
                        .omitEmptyStrings()
                        .trimResults()
                        .split(version)
        )
                .stream()
                .filter(Objects::nonNull)
                .map(Integer::parseInt)
                .collect(toList());
    }
}

package com.mundo.data.datasource;

import java.util.function.Function;

/**
 * PartitionLookupKeyStrategy
 *
 * @author maodh
 * @since 2018/7/25
 */
public interface PartitionLookupKeyStrategy extends Function<Object, String> {
}
package com.mundo.util;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

/**
 * CollectionUtil
 *
 * @author maomao
 * @since 2017/3/4
 */
public final class CollectionUtil {
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Collection<?>... collections) {
        for (Collection<?> collection : collections) {
            if (isEmpty(collection)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isNotEmpty(Collection<?>... collections) {
        for (Collection<?> collection : collections) {
            if (isEmpty(collection)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    public static <E> E findFirstMatch(Collection<E> collection, Predicate<E> predicate) {
        if (isNotEmpty(collection)) {
            for (E e : collection) {
                if (predicate.test(e)) {
                    return e;
                }
            }
        }
        return null;
    }

    public static <E> boolean containsAny(Collection<E> collection1, Collection<E> collection2) {
        if (isNotEmpty(collection1, collection2)) {
            for (E e : collection2) {
                if (collection1.contains(e)) {
                    return true;
                }
            }
        }
        return false;
    }
}
package cn.fantasticmao.mundo.web.filter;

import cn.fantasticmao.mundo.core.support.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * The HTTP request logging filter supports logging in the standard format.
 * <p>
 * When using this class, you'd better provide a logger configuration explicitly.
 * The recommended configuration format such as:
 * <pre>
 *     &lt;appender name="http_request_info" class="ch.qos.logback.core.rolling.RollingFileAppender"&gt;
 *         &lt;file&gt;${log_home}/http-request.log&lt;/file&gt;
 *         &lt;encoder&gt;
 *             &lt;pattern&gt;%date{yyyy-MM-dd HH:mm:ss.SSS} %-5level [%thread] %logger{5}:%L%n%message%n###%n%n&lt;/pattern&gt;
 *             &lt;charset&gt;UTF-8&lt;/charset&gt;
 *         &lt;/encoder&gt;
 *         &lt;rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy"&gt;
 *             &lt;maxHistory&gt;30&lt;/maxHistory&gt;
 *             &lt;fileNamePattern&gt;${log_home}/http-request.%d{yyyy-MM-dd}.log&lt;/fileNamePattern&gt;
 *         &lt;/rollingPolicy&gt;
 *     &lt;/appender&gt;
 *
 *     &lt;logger name="StandardFormatRequestLoggingFilter" level="TRACE" additivity="false"&gt;
 *         &lt;appender-ref ref="http_request_info"/&gt;
 *     &lt;/logger&gt;
 * </pre>
 *
 * @author maomao
 * @version 1.0
 * @since 2019-06-14
 */
public class StandardFormatRequestLoggingFilter extends CommonsRequestLoggingFilter {
    private final Logger logger;

    private static final String SPACE = Constant.Strings.SPACE;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final int DEFAULT_MAX_PAYLOAD_LENGTH = 256;
    private static final String DEFAULT_LOGGER_NAME = "StandardFormatRequestLoggingFilter";

    public StandardFormatRequestLoggingFilter() {
        this(DEFAULT_MAX_PAYLOAD_LENGTH);
    }

    public StandardFormatRequestLoggingFilter(int maxPayloadLength) {
        this(maxPayloadLength, LoggerFactory.getLogger(DEFAULT_LOGGER_NAME));
    }

    public StandardFormatRequestLoggingFilter(int maxPayloadLength, Logger logger) {
        this.logger = logger;
        maxPayloadLength = Math.max(maxPayloadLength, 1024);
        super.setMaxPayloadLength(maxPayloadLength);
    }


    @Override
    @Nonnull
    protected String createMessage(HttpServletRequest request, String prefix, String suffix) {
        final String method = request.getMethod();
        final String uri = request.getRequestURI();
        final String queryString = request.getQueryString();
        final String version = request.getProtocol();
        final Enumeration<String> headerNames = request.getHeaderNames();

        StringBuilder msg = new StringBuilder();
        msg.append(method).append(SPACE).append(uri);
        if (queryString != null) {
            msg.append("?").append(queryString);
        }
        msg.append(SPACE).append(version).append(LINE_SEPARATOR);
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            Enumeration<String> values = request.getHeaders(name);
            while (values.hasMoreElements()) {
                String value = values.nextElement();
                msg.append(name).append(": ").append(value).append(LINE_SEPARATOR);
            }
        }

        final String payload = super.getMessagePayload(request);
        if (payload != null) {
            msg.append(LINE_SEPARATOR).append(payload).append(LINE_SEPARATOR);
        }
        return msg.toString();
    }

    @Override
    protected boolean shouldLog(HttpServletRequest request) {
        return logger.isTraceEnabled();
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        // ignored
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        logger.trace(message);
    }

    @Override
    protected boolean isIncludePayload() {
        return true;
    }
}

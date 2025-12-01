package ikzm_jhm_filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

/**
 * ブラウザのキャッシュを無効化するフィルター。
 * 保護されたリソースへのアクセス後にログアウトした場合、
 * ブラウザバックで情報が見えないようにする。
 */
@WebFilter({
		"/ReportList", "/ReportNew", "/ReportEdit", "/ReportView",
		"/DeleteReport", "/MyReports", "/User/*", "/NewUser"
})
public class NoCacheFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if (response instanceof HttpServletResponse) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;

			// HTTP 1.1
			httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
			// HTTP 1.0
			httpResponse.setHeader("Pragma", "no-cache");
			// Proxies
			httpResponse.setDateHeader("Expires", 0);
		}

		chain.doFilter(request, response);
	}
}
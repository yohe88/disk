package lz.xawl.File.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lz.xawl.File.domain.File;
import lz.xawl.File.service.FileService;

import cn.itcast.servlet.BaseServlet;

public class FileServlet extends BaseServlet {

	private FileService fileService = new FileService();

	// 删除file ,暂时是单个文件 ，后面实现批量删除
	public String deleteFile(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String fid = request.getParameter("fid");
		// 从数据中删除记录，但是还有文件

		File f = fileService.findByFid(fid);
		String filePath = f.getfPath();
		if (("").equals(f.getfHash()) || f.getfHash().isEmpty()) {
			// 说明他是被封人家的 只删除数据库的信息
			fileService.deleteByFid(fid);
			response.getWriter().write("true");
			return null;
		} else {
			File f1 = fileService.findByPath(filePath);

			try {
				java.io.File abf = new java.io.File(this.getServletContext()
						.getRealPath(filePath));
				fileService.deleteByFid(fid);
				abf.delete();
			} catch (Exception e) {
				response.getWriter().write("false");
				return null;
			}
			response.getWriter().write("true");
			return null;
		}
	}

}

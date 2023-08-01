package net.etfbl.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/documents")
public class DocumentService {

	private static Handler handler;
	private static Set<String> documents = new HashSet<>();
	private static final String EXT = ".zip";
	private static final String PATH = ".\\dokuments";

	static {
		try {
			handler = new FileHandler("FileServer.log");
			Logger.getLogger(DocumentService.class.getName()).setUseParentHandlers(false);
			Logger.getLogger(DocumentService.class.getName()).addHandler(handler);
			loadDocuments();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@GET
	public Set<String> getNames() {
		loadDocuments();
		return documents;
	}

	@GET
	@Path("/{documentName}")
	public Response dohvatiDokument(@PathParam("documentName") String documentName) {
		try {
			File file = new File(PATH + File.separator + documentName + EXT);
			FileInputStream fis = new FileInputStream(file);
			byte[] data = new byte[(int) file.length()];
			fis.read(data);
			fis.close();
			return Response.status(200).entity(data).build();
		} catch (Exception ex) {
			Logger.getLogger(DocumentService.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return Response.status(404).build();
		}
	}

	@GET
	@Path("/{documentName}/{size}")
	public Response dohvatiVelicinu(@PathParam("documentName") String documentName) {
		try {
			File file = new File(PATH + File.separator + documentName + EXT);
			byte[] data = new byte[(int) file.length()];
			return Response.status(200).entity(data.length).build();
		} catch (Exception ex) {
			Logger.getLogger(DocumentService.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
			return Response.status(404).build();
		}
	}

	private static void loadDocuments() {
		File file = new File(PATH);
		if (file.listFiles().length != 0) {
			for (File f : file.listFiles()) {
				documents.add(f.getName());
			}
		}
	}

}

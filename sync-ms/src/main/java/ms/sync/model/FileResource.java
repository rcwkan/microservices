package ms.sync.model;

import java.io.File;

import jakarta.ws.rs.FormParam;

public class FileResource {

	@FormParam("id")
	public Long id;

	@FormParam("fileName") 
	public String fileName;

	@FormParam("data")
	public byte[] data;

	@FormParam("file")
	public File file;

}

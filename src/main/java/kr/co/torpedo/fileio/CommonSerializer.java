package kr.co.torpedo.fileio;

import java.io.File;

public class CommonSerializer {
	protected File dirfile;
	protected File makefile;
	protected String fileName;
	protected String fileNameIntern;
	
	public File getDirfile() {
		return dirfile;
	}
	public void setDirfile(File dirfile) {
		this.dirfile = dirfile;
	}
	public File getMakefile() {
		return makefile;
	}
	public void setMakefile(File makefile) {
		this.makefile = makefile;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileNameIntern() {
		return fileNameIntern;
	}
	public void setFileNameIntern(String fileNameIntern) {
		this.fileNameIntern = fileNameIntern;
	}
}

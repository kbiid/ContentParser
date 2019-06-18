package kr.co.torpedo.fileio.manager;

import java.io.File;
import java.io.IOException;

import kr.co.torpedo.fileio.parser.Parser;

public class FileManager {
	private File dirfile;
	private File makefile;
	private String fileName;
	private String fileNameIntern;
	private String dir;
	private DataManager dataManager;

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

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public DataManager getDataManager() {
		return dataManager;
	}

	public void setDataManager(DataManager dataManager) {
		this.dataManager = dataManager;
	}

	public void setSawonFilePath() {
		makefile = new File(getDir() + fileName);
	}

	public void setInterFilenPath() {
		makefile = new File(getDir() + fileNameIntern);
	}

	public void makeDirFile() {
		dirfile = new File(getDir());
	}

	public void checkAndMakeDir() {
		if (dirfile == null) { // dirfile 변수가 null 일때
			Parser.invalidFileLogger.error("dirfile is NullException!");
			throw new NullPointerException("dirfile is NullException!");
		}
		if (!dirfile.exists()) { // 폴더가 없는 경우
			if (dirfile.mkdir()) {
				Parser.invalidFileLogger.info("folder make success");
			} else {
				Parser.invalidFileLogger.error("folder make fail");
				throw new NullPointerException("폴더 생성 실패!");
			}
		} else {
			Parser.invalidFileLogger.info("folder already exist");
		}
	}

	public void checkAndMakeFile() {
		if (makefile == null) { // makefile 변수가 null 일때
			Parser.invalidFileLogger.error("makeFile is NullException!");
			throw new NullPointerException("makeFile is NullException!");
		}
		if (!makefile.exists()) { // 파일이 없는 경우
			try {
				if (makefile.createNewFile()) {
					Parser.invalidFileLogger.info("File make Success ");
				} else {
					Parser.invalidFileLogger.error("FileManager make File NullException!");
					throw new NullPointerException("FileManager make File NullException!");
				}
			} catch (IOException e) {
				Parser.invalidFileLogger.error("Serializer Exception : " + e);
			}
		} else {
			Parser.invalidFileLogger.info("File exist");
		}
	}
}
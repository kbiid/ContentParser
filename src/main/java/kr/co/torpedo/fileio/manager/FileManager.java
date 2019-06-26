package kr.co.torpedo.fileio.manager;

import java.io.File;
import java.io.IOException;

import kr.co.torpedo.fileio.parser.Parser;

public class FileManager {
	private File baseDirFile;
	private File resultMadeFile;
	private String fileName;

	public File getResultMadeFile() {
		return resultMadeFile;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void makeResultFile() {
		resultMadeFile = new File(baseDirFile.getAbsolutePath() + "\\"+ fileName);
	}
	
	public void makeBaseDirFile(String fileBaseDirPath) {
		baseDirFile = new File(fileBaseDirPath);
	}

	public void checkAndMakeDir() {
		if (baseDirFile == null) { // dirfile 변수가 null 일때
			Parser.invalidFileLogger.error("dirfile is NullException!");
			throw new NullPointerException("dirfile is NullException!");
		}
		if (!baseDirFile.exists()) { // 폴더가 없는 경우
			if (baseDirFile.mkdir()) {
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
		if (resultMadeFile == null) { // makefile 변수가 null 일때
			Parser.invalidFileLogger.error("makeFile is NullException!");
			throw new NullPointerException("makeFile is NullException!");
		}
		if (!resultMadeFile.exists()) { // 파일이 없는 경우
			try {
				if (resultMadeFile.createNewFile()) {
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

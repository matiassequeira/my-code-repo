/**
 * 
 */
package org.kuokuo.scan;

import java.io.File;
import java.io.FileFilter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.kuokuo.Constants;
import org.kuokuo.db.HibernateUtil;
import org.kuokuo.db.KoukouFile;
import org.kuokuo.db.KoukouFileService;

class KouKouFileFilter implements FileFilter, Constants {

	private int fileType = 0;

	public KouKouFileFilter(int fileType) {
		this.fileType = fileType;
	}

	@Override
	public boolean accept(File file) {
		if (this.fileType == FILE_TYPE_ALL) {
			return true;
		}
		if (file.isDirectory()) {
			return true;
		}
		String tmp = file.getName().toLowerCase();
		String[] filters = null;
		if (this.fileType == FILE_TYPE_MOVIE) {
			filters = MOVIE_EXTENTIONS;
		} else if (this.fileType == FILE_TYPE_MUSIC) {
			filters = MUSIC_EXTENTIONS;
		} else {
			return false;
		}
		for (String filter : filters) {
			if (tmp.endsWith(filter))
				return true;
		}
		return false;
	}
}

/**
 * @author Felix
 * 
 */
public class FileScaner implements Constants {

	public static FileFilter geFileFilter(int fileType) {
		return new KouKouFileFilter(fileType);
	}

	public static synchronized List<KoukouFile> scanFolder(String rootPath,
			int fileType) {
		List<KoukouFile> koukouFiles = new ArrayList<KoukouFile>();
		recursiveSearch(rootPath, geFileFilter(fileType), koukouFiles);
		return koukouFiles;
	}

	private static void recursiveSearch(String rootPath, FileFilter fileFilter,
			List<KoukouFile> koukouFiles) {
		File fileDir = new File(rootPath);
		if (fileDir.exists() == false) {
			return;
		}
		File[] files = null;
		if (fileFilter == null) {
			files = fileDir.listFiles();
		} else {
			files = fileDir.listFiles(fileFilter);
		}
		for (File file : files) {
			KoukouFile koukouFile = new KoukouFile();
			if (file.isFile()) {
				koukouFile.setName(FilenameUtils.getBaseName(file.getName()));
				koukouFile.setFullName(file.getName());
				koukouFile.setExtName(FilenameUtils
						.getExtension(file.getName()));
				koukouFile.setPath(file.getAbsolutePath());
				koukouFile.setLastModifiedTime(new Timestamp(file
						.lastModified()));
				koukouFile.setFolderFlag(false);
				koukouFiles.add(koukouFile);
			} else if (file.isDirectory()) {
				koukouFile.setName(file.getName());
				koukouFile.setFullName(file.getName());
				koukouFile.setExtName("");
				koukouFile.setPath(file.getAbsolutePath());
				koukouFile.setLastModifiedTime(new Timestamp(file
						.lastModified()));
				koukouFile.setFolderFlag(true);
				koukouFiles.add(koukouFile);
				recursiveSearch(file.getAbsolutePath(), fileFilter, koukouFiles);
			}
		}
		return;
	}

	/**
	 * @param rootFolder
	 */
	public static void scanAndUpdateDb(String rootFolder) {
		List<KoukouFile> koukouFiles = FileScaner.scanFolder(rootFolder,
				FileScaner.FILE_TYPE_MOVIE);
		KoukouFileService service = new KoukouFileService();
		service.deleteAll();
		for (KoukouFile koukouFile : koukouFiles) {
			service.save(koukouFile);
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String rootFolder = "\\\\10.32.177.10/entertainment$/NewArrival_HD";
		scanAndUpdateDb(rootFolder);
		HibernateUtil.closeSessionFactory();

	}
}

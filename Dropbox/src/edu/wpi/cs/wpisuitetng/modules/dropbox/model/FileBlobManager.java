package edu.wpi.cs.wpisuitetng.modules.dropbox.model;

import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;

public class FileBlobManager implements EntityManager<FileBlob> {

	private final Data db;

	public FileBlobManager(Data db) {
		this.db = db;
	}

	@Override
	public FileBlob makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {

		// Parse the FileBlob from JSON
		final FileBlob newFileBlob = FileBlob.fromJSON(content);

		// Update the FileIndex
		List<Model> models = db.retrieveAll(new FileIndex(), s.getProject());
		FileIndex fileIndex;
		
		// Create the index if it does not exist
		if (models.size() < 1) {
			fileIndex = new FileIndex();
		}
		// Retrieve the index if it already exists
		else if (models.size() == 1) {
			fileIndex = (FileIndex) models.get(0);
		}
		else { // Should only ever be on index
			throw new WPISuiteException();
		}
		
		fileIndex.addFile(newFileBlob);
		db.save(fileIndex, s.getProject());
		db.save(fileIndex.getFileNames());
		db.save(fileIndex.getFileIds());
		
		// Save the FileBlob in the database
		if (!db.save(newFileBlob, s.getProject())) {
			throw new WPISuiteException();
		}

		// Return an empty FileBlob to avoid sending the whole file back to the client
		return new FileBlob(new byte[1], "");
	}

	@Override
	public FileBlob[] getEntity(Session s, String id) throws NotFoundException,
	WPISuiteException {
		final int intId = Integer.parseInt(id);
		if(intId < 1) {
			throw new NotFoundException();
		}
		FileBlob[] fileBlobs = null;
		try {
			fileBlobs = db.retrieve(FileBlob.class, "id", intId, s.getProject()).toArray(new FileBlob[0]);
		} catch (WPISuiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(fileBlobs.length < 1 || fileBlobs[0] == null) {
			throw new NotFoundException();
		}
		return fileBlobs;
	}

	@Override
	public FileBlob[] getAll(Session s) throws WPISuiteException {

		List<Model> fileBlobs = db.retrieveAll(new FileBlob(new byte[1], ""), s.getProject());

		return fileBlobs.toArray(new FileBlob[0]);
	}

	@Override
	public FileBlob update(Session s, String content) throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Session s, FileBlob model) throws WPISuiteException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		// TODO Auto-generated method stub

	}

	@Override
	public int Count() throws WPISuiteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

}

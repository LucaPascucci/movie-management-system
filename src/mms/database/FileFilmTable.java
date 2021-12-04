package mms.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import mms.database.interfaces.IFileFilmTable;
import mms.model.FileFilm;

/**
 * Table for the file film.
 * @author Luca Pascucci, Filippo Nicolini, Alessandro Bagnoli
 *
 */
public class FileFilmTable extends AbstractTable implements IFileFilmTable {

	/**
	 * Constructor for this class.
	 * @throws SQLException SQLException
	 */
	public FileFilmTable() throws SQLException {
		super("FILE_FILM");
	}

	@Override
	public void createTable() throws SQLException {
		this.startConnection(CREATING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = this.connection.createStatement();
		statement.executeUpdate(
				"IF NOT EXISTS (SELECT * FROM sysobjects WHERE id = object_id(N'[dbo].[" + this.tableName + "]') AND OBJECTPROPERTY(id, N'IsUserTable') = 1)"
						+ "CREATE TABLE " + this.tableName + " ("
						+ "CodFilm INT NOT NULL,"
						+ "CodNegozio INT NOT NULL, " 
						+ "Nome INT NOT NULL, "
						+ "Estensione VARCHAR(30) NOT NULL, "
						+ "CONSTRAINT IDFILE PRIMARY KEY (CodFilm, CodNegozio, Nome)"
						+ ") "
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public void persist(final Object record) throws SQLException {
		final FileFilm fileFilm = (FileFilm) record;
		this.startConnection(SAVING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "insert into " + this.tableName + " (CodFilm, CodNegozio, Nome, Estensione) values (?,?,?,?)";
		final PreparedStatement statement = this.connection.prepareStatement(insert);
		statement.setInt(1, fileFilm.getCodFilm());
		statement.setInt(2, fileFilm.getCodShop());
		statement.setInt(3, fileFilm.getName());
		statement.setString(4, fileFilm.getExtension());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public void update(final Object record) throws SQLException {
		final FileFilm fileFilm = (FileFilm) record;
		final FileFilm oldFileFilm = findByPrimaryKey(fileFilm.getCodShop(), fileFilm.getCodFilm(), fileFilm.getName());
		if (oldFileFilm != null) {
			final String insert = "update " + this.tableName 
					+ " set Estensione = ? where CodNegozio = ? and CodFilm = ? and Nome = ?";
			this.startConnection(UPDATING_RECORD + " " + this.tableName + " " + TABLE);
			final PreparedStatement statement = this.connection.prepareStatement(insert);
			statement.setString(1, fileFilm.getExtension());
			statement.setInt(2, oldFileFilm.getCodShop());
			statement.setInt(3, oldFileFilm.getCodFilm());
			statement.setInt(4, oldFileFilm.getName());
			statement.executeUpdate();
			statement.close();
			this.closeConnection();
		}

	}

	@Override
	public void delete(final Object record) throws SQLException {
		final FileFilm fileFilm = (FileFilm) record;
		this.startConnection(DELETING_RECORD + " " + this.tableName + " " + TABLE);
		final String insert = "delete from " + this.tableName + " where CodNegozio = ? and CodFilm = ? and Nome = ? order by Nome";
		final PreparedStatement statement = this.connection.prepareStatement(insert);
		statement.setInt(1, fileFilm.getCodShop());
		statement.setInt(2, fileFilm.getCodFilm());
		statement.setInt(3, fileFilm.getName());
		statement.executeUpdate();
		statement.close();
		this.closeConnection();
	}

	@Override
	public FileFilm findByPrimaryKey(final int codShop, final int codFilm, final int name) throws SQLException {

		FileFilm fileFilm = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " where CodNegozio = ? and CodFilm = ? and Nome = ?";
		final PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, codShop);
		statement.setInt(2, codFilm);
		statement.setInt(3, name);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			fileFilm = new FileFilm();
			fileFilm.setCodFilm(result.getInt("CodFilm"));
			fileFilm.setCodShop(result.getInt("CodNegozio"));
			fileFilm.setName(result.getInt("Nome"));
			fileFilm.setExtension(result.getString("Estensione"));
		}
		statement.close();
		this.closeConnection();
		return fileFilm;
	}

	@Override
	public void alterTable() throws SQLException {
		this.startConnection(ALTERING_TABLE + " " + this.tableName + " " + TABLE);
		final Statement statement = connection.createStatement();
		statement.executeUpdate(
				"ALTER TABLE " + this.tableName + " ADD CONSTRAINT FKlink "
						+ "FOREIGN KEY (CodFilm, CodNegozio) "
						+ "REFERENCES COPIA_FILM "
						+ "ON DELETE CASCADE"
				);
		statement.close();
		this.closeConnection();
	}

	@Override
	public Integer getNextName(final int codShop, final int codFilm, final String extension) throws SQLException {
		Integer maxName = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select MAX(Nome) as MaxName from " + this.tableName + " where CodNegozio = ? and CodFilm = ? and Estensione = ?";
		final PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, codShop);
		statement.setInt(2, codFilm);
		statement.setString(3, extension);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			maxName = result.getInt("MaxName");
		}
		result.close();
		statement.close();
		this.closeConnection();
		return maxName;
	}

	@Override
	public List<FileFilm> getFilmCoversinShop(final int codShop, final int codFilm, final String extension) throws SQLException {
		List<FileFilm> covers = null;
		FileFilm file = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " where CodNegozio = ? and CodFilm = ? and Estensione = ?";
		final PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, codShop);
		statement.setInt(2, codFilm);
		statement.setString(3, extension);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			covers = new LinkedList<>();
			file = new FileFilm();
			file.setCodFilm(result.getInt("CodFilm"));
			file.setCodShop(result.getInt("CodNegozio"));
			file.setName(result.getInt("Nome"));
			file.setExtension(result.getString("Estensione"));
			covers.add(file);
		}
		while (result.next()) {
			file = new FileFilm();
			file.setCodFilm(result.getInt("CodFilm"));
			file.setCodShop(result.getInt("CodNegozio"));
			file.setName(result.getInt("Nome"));
			file.setExtension(result.getString("Estensione"));
			covers.add(file);
		}
		result.close();
		statement.close();
		this.closeConnection();
		return covers;
	}

	@Override
	public List<FileFilm> getFileofFilminShop(final int codShop, final int codFilm) throws SQLException {
		List<FileFilm> files = null;
		FileFilm file = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " where CodNegozio = ? and CodFilm = ?";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setInt(1, codShop);
		statement.setInt(2, codFilm);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			files = new LinkedList<>();
			file = new FileFilm();
			file.setCodFilm(result.getInt("CodFilm"));
			file.setCodShop(result.getInt("CodNegozio"));
			file.setName(result.getInt("Nome"));
			file.setExtension(result.getString("Estensione"));
			files.add(file);
		}
		while (result.next()) {
			file = new FileFilm();
			file.setCodFilm(result.getInt("CodFilm"));
			file.setCodShop(result.getInt("CodNegozio"));
			file.setName(result.getInt("Nome"));
			file.setExtension(result.getString("Estensione"));
			files.add(file);
		}
		result.close();
		statement.close();
		this.closeConnection();
		return files;
	}

	@Override
	public List<FileFilm> allFilesOfFilm(final int codFilm) throws SQLException {
		List<FileFilm> files = null;
		FileFilm file = null;
		this.startConnection(FINDING_RECORD + " " + this.tableName + " " + TABLE);
		final String query = "select * from " + this.tableName + " where CodFilm = ?";
		final PreparedStatement statement = this.connection.prepareStatement(query);
		statement.setInt(1, codFilm);
		final ResultSet result = statement.executeQuery();
		if (result.next()) {
			files = new LinkedList<>();
			file = new FileFilm();
			file.setCodFilm(result.getInt("CodFilm"));
			file.setCodShop(result.getInt("CodNegozio"));
			file.setName(result.getInt("Nome"));
			file.setExtension(result.getString("Estensione"));
			files.add(file);
		}
		while (result.next()) {
			file = new FileFilm();
			file.setCodFilm(result.getInt("CodFilm"));
			file.setCodShop(result.getInt("CodNegozio"));
			file.setName(result.getInt("Nome"));
			file.setExtension(result.getString("Estensione"));
			files.add(file);
		}
		result.close();
		statement.close();
		this.closeConnection();
		return files;
	}
}

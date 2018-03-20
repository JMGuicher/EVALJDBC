import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class foodProjet {
	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		// connection à la base de données SQL

		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://baasu.db.elephantsql.com:5432/csakxqni",
					"csakxqni", "_5cERvfmNRAR5hPWNneHbvKMLl6nsiuN");

		} catch (SQLException e) {

			e.printStackTrace();
		}

		String menuChoice = "";
		String aliment;
		int energie;
		int proteine;
		int glucide;
		int lipide;

		while (!menuChoice.equals("0")) {

			// création du menu
			System.out.println("");
			System.out.println("Bienvenue dans l'application foodProjet de Jim! ");
			System.out.println("-----------------------------------------------");
			System.out.println("1) Ajouter un aliment en base de données");
			System.out.println("2) Supprimer un aliment de la base de données");
			System.out.println("3) Afficher toute la liste");
			System.out.println("0) Quitter le programme");
			menuChoice = scanner.next();

			switch (menuChoice) {
			case "1":
				System.out.println("Saisissez l'aliment : ");
				aliment = scanner.next();
				System.out.println("Saisissez la valeur énergétique : ");
				energie = scanner.nextInt();
				System.out.println("Saisissez la valeur en protéine : ");
				proteine = scanner.nextInt();
				System.out.println("Saisissez la valeur en glucide : ");
				glucide = scanner.nextInt();
				System.out.println("Saisissez la valeur en lipide : ");
				lipide = scanner.nextInt();

				ajoutAliment(connection, aliment, energie, proteine, glucide, lipide);

				// ajout de la catégorie
				System.out.println("Quelle est la catégorie : ");
				String categorie1 = scanner.next();

				ajoutCategorie(connection, categorie1);

				break;

			case "2":
				lister(connection);
				System.out.println("saisissez l'ID que vous souhaitez supprimer : ");
				int ID = scanner.nextInt();
				supprimAliment(connection, ID);
				// supprimCategory(connection, ID);

				break;

			case "3":
				lister(connection);

				break;

			case "0":
				System.out.println("bye bye!");
				break;

			default:
				System.out.println("Commande inconnue ! " + menuChoice);
				break;

			}

		}

		System.out.println("Connection réussie à la base");

		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ecriture d'une méthode qui permet d'ajouter un aliment
	public static void ajoutAliment(Connection connection, String nom, int energie, int proteine, int glucide,
			int lipide) {

		// ajouter un aliment
		String sql = "insert into alimente (nom, energie, proteine, glucide, lipide) values (?, ?, ?, ?, ?)";
		PreparedStatement sqlbase;
		try {
			sqlbase = connection.prepareStatement(sql, Statement.NO_GENERATED_KEYS);
			sqlbase.setString(1, nom);
			sqlbase.setInt(2, energie);
			sqlbase.setInt(3, proteine);
			sqlbase.setInt(4, glucide);
			sqlbase.setInt(5, lipide);
			sqlbase.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

	}

	// écriture d'une méthode qui permet d'ajouter une categorie
	public static void ajoutCategorie(Connection connection, String categorie) {

		String sql = "insert into categoriese (cat) values (?)";
		PreparedStatement sqlbase;
		try {
			sqlbase = connection.prepareStatement(sql, Statement.NO_GENERATED_KEYS);
			sqlbase.setString(1, categorie);
			sqlbase.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}

	// écriture d'une méthode qui permet de supprimer un aliment
	public static void supprimAliment(Connection connection, int id) {
		String sql = "DELETE FROM categoriese WHERE id_cat= (?)";
		PreparedStatement sqlbase;
		try {
			sqlbase = connection.prepareStatement(sql, Statement.NO_GENERATED_KEYS);
			sqlbase.setInt(1, id);
			sqlbase.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
			return;
		}

		String sql1 = "DELETE FROM alimente where id= (?)";
		// categoriese WHERE id_cat= (?);"
		PreparedStatement sqlbase1;
		try {
			sqlbase1 = connection.prepareStatement(sql1, Statement.NO_GENERATED_KEYS);
			sqlbase1.setInt(1, id);
			sqlbase1.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
			return;
		}

	}

	// écriture d'une méthode qui permet de supprimer une catégorie
	/*
	 * public static void supprimCategory(Connection connection, int id) { String
	 * sql = "DELETE FROM categoriese WHERE id_cat= (?)"; PreparedStatement sqlbase;
	 * try { sqlbase = connection.prepareStatement(sql,
	 * Statement.NO_GENERATED_KEYS); sqlbase.setInt(1, id); sqlbase.executeUpdate();
	 * 
	 * } catch (SQLException e) {
	 * 
	 * e.printStackTrace(); return; }
	 * 
	 * }
	 */

	// écriture d'une méthode qui permet de lister
	public static void lister(Connection connection) {
		String sql = "SELECT * FROM alimente";
		PreparedStatement sqlbase;
		try {
			sqlbase = connection.prepareStatement(sql, Statement.NO_GENERATED_KEYS);

			ResultSet res = sqlbase.executeQuery();
			while (res.next()) {
				System.out.println(res.getInt("id") + "  " + "ALIMENT: " + res.getString("nom") + "  "
						+ "Valeur énergétique:" + res.getInt("energie") + "  " + "Valeur en proteine:"
						+ res.getInt("proteine") + "  " + "Valeur en glucide:" + res.getInt("glucide") + "  "
						+ "Valeur en lipide: " + res.getInt("lipide"));
			}

		} catch (SQLException e) {

			e.printStackTrace();
			return;
		}

	}

}

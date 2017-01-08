package example.avro;

import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericEnumSymbol;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

public class AvroTest1 {
	@Test public void testWithCodeGen() throws IOException {
		System.out.println("testWithCodeGen");
		User user1 = new User();
		user1.setName("Alyssa");
		user1.setFavoriteNumber(256);
		user1.setSex(SEX.FEMALE);
		// Leave favorite color null

		// Alternate constructor
		User user2 = new User("Ben", 7, "red", SEX.MALE);

		// Construct via builder
		User user3 = User.newBuilder()
		             .setName("Charlie")
		             .setFavoriteColor("blue")
		             .setFavoriteNumber(null)
		             .setSex(SEX.UNKOWN)
		             .build();
		
		File file = new File("users1.avro");
		
		DatumWriter<User> userDatumWriter = new SpecificDatumWriter<User>(User.class);
		DataFileWriter<User> dataFileWriter = new DataFileWriter<User>(userDatumWriter);
		dataFileWriter.create(user1.getSchema(), file);
		dataFileWriter.append(user1);
		dataFileWriter.append(user2);
		dataFileWriter.append(user3);
		dataFileWriter.close();
		
		// Deserialize Users from disk
		DatumReader<User> userDatumReader = new SpecificDatumReader<User>(User.class);
		DataFileReader<User> dataFileReader = new DataFileReader<User>(file, userDatumReader);
		User user = null;
		while (dataFileReader.hasNext()) {
		// Reuse user object by passing it to next(). This saves us from
		// allocating and garbage collecting many objects for files with
		// many items.
		user = dataFileReader.next(user);
		System.out.println(user);
		}
	}
	
	@Test public void testWithoutCodeGen() throws IOException {
		System.out.println("testWithoutCodeGen");
		File schemaFile = new File("src/main/avro/user.avsc");
		
		System.out.println("schema: " + schemaFile.getAbsolutePath());
		
		Schema schema = new Schema.Parser().parse(schemaFile);
		GenericRecord user1 = new GenericData.Record(schema);
		GenericEnumSymbol sex1 = new GenericData.EnumSymbol(schema.getField("sex").schema(), "MALE");
		
		user1.put("name", "Alyssa");
		user1.put("favorite_number", 256);
		user1.put("sex", sex1);
		// Leave favorite color null

		GenericRecord user2 = new GenericData.Record(schema);
		GenericEnumSymbol sex2 = new GenericData.EnumSymbol(schema.getField("sex").schema(), "FEMALE");
		user2.put("name", "Ben");
		user2.put("favorite_number", 7);
		user2.put("favorite_color", "red");
		user2.put("sex", sex2);
		
		// Serialize user1 and user2 to disk
		File file = new File("users2.avro");
		DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<GenericRecord>(schema);
		DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<GenericRecord>(datumWriter);
		dataFileWriter.create(schema, file);
		dataFileWriter.append(user1);
		dataFileWriter.append(user2);
		dataFileWriter.close();
		
		// Deserialize users from disk
		DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(schema);
		DataFileReader<GenericRecord> dataFileReader = new DataFileReader<GenericRecord>(file, datumReader);
		GenericRecord user = null;
		while (dataFileReader.hasNext()) {
		// Reuse user object by passing it to next(). This saves us from
		// allocating and garbage collecting many objects for files with
		// many items.
		user = dataFileReader.next(user);
		System.out.println(user);
		
		}
	}
	
}

package example.avro;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

public class AvroTest2 {
	@Test public void testWithCodeGen() throws IOException {
		System.out.println("testWithCodeGen");
		User user1 = new User();
		user1.setName("Alyssa");
		user1.setFavoriteNumber(256);
		// Leave favorite color null

		// Alternate constructor
		User user2 = new User("Ben", 7, "red", SEX.MALE);

		// Construct via builder
		User user3 = User.newBuilder()
		             .setName("Charlie")
		             .setFavoriteColor("blue")
		             .setFavoriteNumber(null)
		             .build();	
		
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
		DatumWriter<User> writer = new SpecificDatumWriter<User>(User.getClassSchema());

		writer.write(user1, encoder);
		encoder.flush();
		out.close();
		byte[] serializedBytes = out.toByteArray();
		
		System.out.println("data:" +serializedBytes.toString());
		
		DatumReader<User> reader = new SpecificDatumReader<User>(User.getClassSchema());
		
		Decoder decoder = DecoderFactory.get().binaryDecoder(serializedBytes, null);
		User user = reader.read(null, decoder);
		
		System.out.println("user:" + user.toString());

	}

	
}

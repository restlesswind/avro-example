

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

public class AvroRelationTest {
	@Test public void testWithCodeGen() throws IOException {
		System.out.println("testWithCodeGen");
		Relation r1 = new Relation("fqdn1", Op.ADD);
		Relation r2 = new Relation("fqdn2", Op.DELETE);
		List<Relation> list = new ArrayList<>();
		list.add(r1);
		list.add(r2);
		
		
		RelationSet rs1 = new RelationSet();
		rs1.setOpType(OpType.SET);
		rs1.setRelations(list);

		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
		DatumWriter<RelationSet> writer = new SpecificDatumWriter<RelationSet>(RelationSet.getClassSchema());

		writer.write(rs1, encoder);
		writer.write(rs1, encoder);
		encoder.flush();
		out.close();
		byte[] serializedBytes = out.toByteArray();
		
		System.out.println("data:" +serializedBytes.toString());
		
		DatumReader<RelationSet> reader = new SpecificDatumReader<RelationSet>(RelationSet.getClassSchema());
		
		Decoder decoder = DecoderFactory.get().binaryDecoder(serializedBytes, null);
		RelationSet rs;
		while((rs = reader.read(null, decoder) )!= null) {
			System.out.println("rs:" + rs.toString());
		}
		
		

	}

	
}

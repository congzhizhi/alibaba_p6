package serializer;

import org.apache.kafka.common.serialization.Serializer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Map;

public class CompanySerializer implements Serializer<Company> {
    private String encoding = "UTF-8";
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, Company company) {
        if(company == null){
            return null;
        }
        byte[] name,address;
        try {
            if (company.getName() != null){
                name = company.getName().getBytes(encoding);
            }else{
                name = new byte[0];
            }
            if (company.getAddress() != null){
                address = company.getAddress().getBytes(encoding);
            }else {
                address = new byte[0];
            }
            ByteBuffer buffer = ByteBuffer.allocate(4+4+name.length+address.length);
            buffer.putInt(name.length);
            buffer.put(name);
            buffer.putInt(address.length);
            buffer.put(address);
            return buffer.array();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @Override
    public void close() {

    }
}

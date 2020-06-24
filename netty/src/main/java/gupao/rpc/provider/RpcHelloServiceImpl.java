package gupao.rpc.provider;


import gupao.rpc.api.IRpcHelloService;

public class RpcHelloServiceImpl implements IRpcHelloService {

    @Override
    public String hello(String name) {
        return "Hello " + name + "!";  
    }  
  
}  

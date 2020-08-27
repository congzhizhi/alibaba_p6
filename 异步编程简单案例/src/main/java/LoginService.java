import java.util.function.Function;

public class LoginService {


    public void login(String username,String password,Function<UserEntity, Void> callback){
        DaoThread.process(new AsyncImpl(username,password){
            @Override
            public void doFinish() {
                System.out.println("主线程回调："+this.getUserEntity());
                callback.apply(this.getUserEntity());
            }
        });

    }



    class AsyncImpl implements IAsynService{

        String username;
        String password;
        UserEntity userEntity;
        AsyncImpl(String username,String password){
            this.username = username;
            this.password = password;
        }

        @Override
        public void doProcess() {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(1);
            userEntity.setUsername(this.username);
            userEntity.setPassword(this.password);
            this.userEntity = userEntity;
        }

        public UserEntity getUserEntity(){
            return this.userEntity;
        }

    }
}

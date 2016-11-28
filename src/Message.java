/**
 * Created by yeonwoo_kim on 11/7/16.
 */
public class Message {
    private MessageName messageName;
    private String nameArg;

    public Message(MessageName messageName) {
        this.messageName = messageName;
        nameArg = "";
    }

    public MessageName getMessageName() { return messageName; }

    public String getNameArg() { return nameArg; }

    public void setNameArg(String nameArg) { this.nameArg = nameArg; }
}

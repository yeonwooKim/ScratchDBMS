/**
 * Created by yeonwoo_kim on 11/7/16.
 */
public class Message {
    private MessageName messagename;
    private String nameArg;

    public Message(MessageName messagename) {
        this.messagename = messagename;
        nameArg = "";
    }

    public Message(MessageName messagename, String nameArg) {
        this.messagename = messagename;
        this.nameArg = nameArg;
    }

    public MessageName getMessagename() {
        return messagename;
    }

    public String getNameArg() {
        return nameArg;
    }
}

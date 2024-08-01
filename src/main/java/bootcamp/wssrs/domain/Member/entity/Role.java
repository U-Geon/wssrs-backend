package bootcamp.wssrs.domain.Member.entity;

public enum Role {
    USER, ADMIN;

    @Override
    public String toString() {
        return "ROLE_" + super.toString();
    }
}

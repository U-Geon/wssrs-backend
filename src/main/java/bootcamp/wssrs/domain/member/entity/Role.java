package bootcamp.wssrs.domain.member.entity;

public enum Role {
    USER, ADMIN;

    @Override
    public String toString() {
        return "ROLE_" + super.toString();
    }
}

package pl.sda.javawwa18;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;
import pl.sda.javawwa18.domain.Email;
import pl.sda.javawwa18.domain.EmailMapped;
import pl.sda.javawwa18.domain.Message;
import pl.sda.javawwa18.domain.MessageMapped;
import pl.sda.javawwa18.util.SessionUtil;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

public class RelationsTest {

    @Test
    public void testNonManagedRelationship() {
        Long emailId, msgId;
        Email email;
        Message msg;

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            email = new Email();
            msg = new Message();

            email.setMessage(msg);
            //msg.setEmail(email);

            session.save(email);
            session.save(msg);

            emailId = email.getId();
            msgId = msg.getId();

            tx.commit();
        }

        assertNotNull(email.getMessage());
        assertNull(msg.getEmail());

        try(Session session = SessionUtil.getSession()) {
            email = session.get(Email.class, emailId);
            msg = session.get(Message.class, msgId);
        }

        assertNotNull(email.getMessage());
        assertNull(msg.getEmail());
    }

    @Test
    public void testManuallyManagedRelationship() {
        Long emailId, msgId;
        Email email;
        Message msg;

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            email = new Email();
            msg = new Message();

            email.setMessage(msg);
            msg.setEmail(email);

            session.save(email);
            session.save(msg);

            emailId = email.getId();
            msgId = msg.getId();

            tx.commit();
        }

        assertNotNull(email.getMessage());
        assertNotNull(msg.getEmail());

        try(Session session = SessionUtil.getSession()) {
            email = session.get(Email.class, emailId);
            msg = session.get(Message.class, msgId);
        }

        assertNotNull(email.getMessage());
        assertNotNull(msg.getEmail());
    }

    @Test
    public void testManagedRelationship() {
        Long emailId, msgId;
        EmailMapped email;
        MessageMapped msg;

        try(Session session = SessionUtil.getSession()) {
            Transaction tx = session.beginTransaction();

            email = new EmailMapped();
            email.setSubject("JavaWWA13");
            msg = new MessageMapped();
            msg.setContent("See you @02.02.2019!");

            //email.setMessage(msg);
            msg.setEmail(email);

            session.save(email);
            session.save(msg);

            emailId = email.getId();
            msgId = msg.getId();

            tx.commit();
        }

        assertEquals(email.getSubject(), "JavaWWA13");
        assertEquals(msg.getContent(), "See you @02.02.2019!");
        assertNull(email.getMessage());
        assertNotNull(msg.getEmail());

        try(Session session = SessionUtil.getSession()) {
            email = session.get(EmailMapped.class, emailId);
            msg = session.get(MessageMapped.class, msgId);
        }

        assertNotNull(email.getMessage());
        assertNotNull(msg.getEmail());
    }

}


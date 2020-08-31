package ru.job4j.hql;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.Collection;
import java.util.function.Function;

public class HbmRun implements Service {
    public final static Logger LOGGER = Logger.getLogger(HbmRun.class.getName());
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private HbmRun() {
    }

    private static final class Lazy {
        private static final Service INST = new HbmRun();
    }

    public static Service instOf() {
        return Lazy.INST;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Integer createCandidate(Candidate candidate) {
        return (Integer) tx(session -> session.save(candidate));
    }

    @Override
    public Collection<Candidate> findAllCandidate() {
        return tx(session -> session.createQuery("from Candidate").list());
    }

    @Override
    public Candidate findCandidateById(int id) {
        return tx(session -> session.get(Candidate.class, id));
    }

    @Override
    public Candidate findCandidateByName(String name) {
        return tx(session -> {
            final Query query = session.createQuery(
                    "from Candidate cand where cand.name = :fName");
            query.setParameter("fName", name);
            Candidate candidate = (Candidate) query.uniqueResult();
            return candidate;
        });
    }

    @Override
    public void updateCandidate(int id, int salary) {
        tx(session -> {
            final Query query = session.createQuery(
                    "update Candidate cand set cand.salary = :newSalary where cand.id = :fId");
            query.setParameter("fId", id);
            query.setParameter("newSalary", salary);
            query.executeUpdate();
            return null;
        });
    }

    @Override
    public void deleteCandidateById(int id) {
        tx(session -> {
            final Query query = session.createQuery("delete from Candidate where id = :fId");
            query.setParameter("fId", id);
            query.executeUpdate();
            return null;
        });
    }

    public static void main(String[] args) {
        Candidate one = new Candidate("Alex", "21", 2000);
        Candidate two = new Candidate("Nikolay", "28", 1500);
        Candidate three = new Candidate("Nikita", "25", 3000);

        int idAlex = HbmRun.instOf().createCandidate(one);
        int idNikolay = HbmRun.instOf().createCandidate(two);
        int idNikita = HbmRun.instOf().createCandidate(three);
        System.out.println("idAlex = " + idAlex);
        System.out.println("idNikolay " + idNikolay);
        System.out.println("idNikita " + idNikita);

        Collection<Candidate> collection = HbmRun.instOf().findAllCandidate();
        System.out.println(collection);
        collection.forEach(System.out::println);
        System.out.println();

        Candidate cand = HbmRun.instOf().findCandidateById(idAlex);
        System.out.println(cand);

        Candidate cand2 = HbmRun.instOf().findCandidateByName("Nikita");
        System.out.println(cand2.name);

        HbmRun.instOf().deleteCandidateById(idNikolay);
        System.out.println("2 = " + HbmRun.instOf().findAllCandidate().size());

        HbmRun.instOf().updateCandidate(idAlex, 5000);
        System.out.println("5000 = " + HbmRun.instOf().findCandidateById(idAlex).getSalary());
    }

}

package ru.job4j.hql;

import java.util.Collection;

public interface Service {
    Integer createCandidate(Candidate candidate);

    Collection<Candidate> findAllCandidate();

    Candidate findCandidateById(int id);

    Candidate findCandidateByName(String name);

    void updateCandidate(int id, int salary);

    void deleteCandidateById(int id);
}

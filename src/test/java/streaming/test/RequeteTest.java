/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package streaming.test;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import streaming.entity.Film;

/**
 *
 * @author admin
 */
public class RequeteTest {

    @Test
    public void titreFilm4OK() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT f FROM Film f WHERE f.id=4");

        Film f = (Film) query.getSingleResult();

        Assert.assertEquals("Fargo", f.getTitre());
    }

    @Test
    public void nombreFilmsOK() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT COUNT(f) FROM Film f");

        long n = (long) query.getSingleResult();

        Assert.assertEquals(4L, n);
    }

//Année de prod mini de nos films
    @Test
    public void anneeProdvieuxFilmOK() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT MIN(f.annee) FROM Film f ");

        Integer annee = (Integer) query.getSingleResult();

        Assert.assertTrue(1968 == annee);

    }

    @Test
    public void nbLienBigLebowski() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT COUNT(l) FROM Lien l WHERE l.film.titre LIKE '%Big Lebowski%'");

        Long n = (Long) query.getSingleResult();

        Assert.assertTrue(n == 1L);
    }
//Nombre de films réalisés par Polanski

    @Test
    public void nbFilmsRealiseParPolanski() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT COUNT(f) FROM  Film f JOIN f.realisateurs r WHERE r.nom like '%Polanski%'");

        Long n = (Long) query.getSingleResult();

        Assert.assertEquals(2L, n.longValue());
    }
    //Nombre de films interprétés par Polanski

    @Test
    public void NbFilmInterPolanskiOK() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT COUNT(f) FROM Film f JOIN f.acteurs a WHERE a.nom LIKE '%Polanski%'");

        Long n = (Long) query.getSingleResult();

        Assert.assertEquals(1, n.longValue());
    }
    //Nombre de films à la fois interprétés et réalisés par polanski

    @Test
    public void nbFilmJoueEtInterPolanskiOK() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT count(f) FROM Film f JOIN f.acteurs a JOIN f.realisateurs r WHERE r.nom = a.nom AND a.nom LIKE '%Polanski%'");

        Long l = (Long) query.getSingleResult();

        Assert.assertEquals(1L, l.longValue());
    }
    //Le titre du film d'horreur anglais réalisé par roman polanski

    @Test
    public void titreFilmHorreurAnglaisRealByPolanski() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT f.titre FROM Film f JOIN f.pays p JOIN f.realisateurs r WHERE p.nom LIKE 'UK' AND f.genre.nom LIKE 'Horreur' AND r.nom LIKE 'Polanski'");

        String s = query.getSingleResult().toString();

        Assert.assertEquals("Le bal des vampires", s);
    }

    //Le nomnbre de films réalisés par joel coen
    @Test
    public void nbFilmRealJoelCoen() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT COUNT(f) FROM  Film f JOIN f.realisateurs r WHERE r.nom like '%Coen%' and r.prenom LIKE '%Joel%'");

        Long n = (Long) query.getSingleResult();

        Assert.assertEquals(2L, n.longValue());
    }

    //Le nombre de films réalisés par les 2 frères coen
    //
    @Test
    public void nbFilmRealParFCoen() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT COUNT(f) FROM  Film f JOIN f.realisateurs r JOIN f.realisateurs r2 WHERE r.nom like '%Coen%' and r.prenom LIKE '%Joel%' and r2.nom Like '%Coen%' and r2.prenom LIKE '%Ethan%'");

        Long n = (Long) query.getSingleResult();

        Assert.assertEquals(2L, n.longValue());
    }

    //Le nombre de films des frères coen interprétés par Steve Buscemi
    //
    @Test
    public void nbFilmCoenInterParSteve() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT COUNT(f) FROM  Film f JOIN f.realisateurs r JOIN f.realisateurs r2 JOIN f.acteurs a WHERE r.nom like '%Coen%' and r.prenom LIKE '%Joel%' and r2.nom Like '%Coen%' and r2.prenom LIKE '%Ethan%' AND a.nom LIKE '%Buscemi%'");

        Long n = (Long) query.getSingleResult();

        Assert.assertEquals(2L, n.longValue());
    }

    //Le nombre de films policiers des frères Coen interprétés par Steve Buscemi
    //
    @Test
    public void nbFilmPolicierFrCoenInterBuscemi() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT COUNT(f) FROM  Film f JOIN f.realisateurs r JOIN f.realisateurs r2 JOIN f.acteurs a JOIN f.genre g WHERE r.nom like '%Coen%' and r.prenom LIKE '%Joel%' and r2.nom Like '%Coen%' and r2.prenom LIKE '%Ethan%' AND a.nom LIKE '%Buscemi%' AND g.nom LIKE '%Policier%'");

        Long n = (Long) query.getSingleResult();

        Assert.assertEquals(1L, n.longValue());
    }

    //Le nombre de saisons de la série Dexter
    //
    @Test
    public void nbSaisonsDexter() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT COUNT(se) FROM Saison sa JOIN sa.serie se WHERE se.titre LIKE '%Dexter%'");

        Long n = (Long) query.getSingleResult();

        Assert.assertEquals(8L, n.longValue());
    }

    //Le nombre d'épisodes de la saison 8 de la série Dexter
    //
    @Test
    public void nbEpisodeSaison8Dexter() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT COUNT(ep) FROM Saison sa JOIN sa.serie se JOIN sa.episodes ep WHERE se.titre LIKE '%Dexter%' AND sa.numSaison = 8");

        Long n = (Long) query.getSingleResult();

        Assert.assertEquals(12L, n.longValue());
    }
    //Le nombre total d'épisodes de la série Dexter
    //
    @Test
    public void nbEpisodeSerieDexter(){
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT COUNT(ep) FROM Saison sa JOIN sa.serie se JOIN sa.episodes ep WHERE se.titre LIKE '%Dexter%'");

        Long n = (Long) query.getSingleResult();

        Assert.assertEquals(96L, n.longValue());
    }
    //Le nombre total de liens pour nos films policiers américains
    //
    @Test
    public void nbLiensFilmsPoliciets(){
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT COUNT(l) FROM Film f JOIN f.liens l WHERE f.genre.nom = 'Policier'");

        Long n = (Long) query.getSingleResult();

        Assert.assertEquals(3L, n.longValue());
    }
    //Le nombre totals de liens pour nos films d'horreur interprétés par Polanski
    //
    @Test
    public void nbLiensFilmsHorreurPolanski(){
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT count(l) FROM Film f JOIN f.liens l JOIN f.acteurs a WHERE a.nom = 'Polanski' AND f.genre.nom = 'Horreur'");

        Long n = (Long) query.getSingleResult();

        Assert.assertEquals(1L, n.longValue());
    }
    //Tous les films d'horreur, sauf ceux interprétés par Polanski ( utiliser UNION ou MINUS ou INTERSECT )
    //
    @Test
    public void filmDHorreurSansPolanski() {
        EntityManager em = Persistence.createEntityManagerFactory("PU").createEntityManager();

        Query query = em.createQuery("SELECT f FROM Film f WHERE f.genre.nom = 'Horreur' INTERSECT SELECT f FROM Film f JOIN f.acteurs a WHERE a.nom <> 'Polanski'");

        int n = query.getResultList().size();

        Assert.assertEquals(0, n);
    }
    //Parmi tous les films, uniquement ceux interprétés par Polanski  ( utiliser UNION ou MINUS ou INTERSECT )
    //
    
    //Tous les films interprétés par Polanski et aussi tous les films d'horreur ( utiliser UNION ou MINUS ou INTERSECT )

}

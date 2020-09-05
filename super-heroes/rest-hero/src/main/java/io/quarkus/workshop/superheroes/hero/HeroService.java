package io.quarkus.workshop.superheroes.hero;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Transactional(REQUIRED)
public class HeroService {

    @ConfigProperty(name = "level.multiplier", defaultValue="1")
    int levelMultiplier;


    @Transactional(SUPPORTS)
    public List<Hero> findAllHeroes() {
        return Hero.listAll();
    }

    @Transactional(SUPPORTS)
    public Hero findHeroById(Long id) {
        return Hero.findById(id);
    }

    @Transactional(SUPPORTS)
    public Hero findRandomHero() {
        Hero randomHero = null;
        while (randomHero == null) {
            randomHero = Hero.findRandom();
        }
        return randomHero;
    }

    public Hero persistHero(@Valid Hero hero) {
        hero.level = hero.level * levelMultiplier;
        Hero.persist(hero);
        return hero;
    }

    public Hero updateHero(@Valid Hero hero) {
        Hero entity = Hero.findById(hero.id);
        entity.name = hero.name;
        entity.otherName = hero.otherName;
        entity.level = hero.level;
        entity.picture = hero.picture;
        entity.powers = hero.powers;
        return entity;
    }

    public void deleteHero(Long id) {
        Hero hero = Hero.findById(id);
        hero.delete();
    }
}
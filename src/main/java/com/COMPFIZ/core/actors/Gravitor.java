package com.COMPFIZ.core.actors;

import com.COMPFIZ.core.mixins.Constants;
import com.COMPFIZ.core.mixins.maths.Maths;
import com.COMPFIZ.core.models.Entity;
import com.COMPFIZ.core.models.Physics;
import org.joml.Vector3f;


public class Gravitor {
    public static int n = Constants.masses;

    public void update(float interval, Entity planet, Entity[] masses) {
        Vector3f acc = new Vector3f(), force = new Vector3f();
        Vector3f[] gravitational = new Vector3f[masses.length];
        Physics stuff = (Physics) planet.desc;

        for (int i = 0; masses[i] != null; i++){
            if(masses[i] == planet) continue;
            Vector3f d = planet.position.sub(masses[i].position, new Vector3f()).negate();
            if(d.length() == 0) continue;
            gravitational[i] = d.normalize().mul(Maths.calcOrbitForce(stuff.mass, ((Physics) masses[i].desc).mass, d.length()));
        }

        force.set(Maths.calcNetForces(gravitational));
        System.out.println(force + " | " + stuff.name);
        force.div(stuff.mass, acc);
        stuff.v.add(acc.mul(interval));
        planet.position.add(stuff.v.mul(interval, new Vector3f()));
    }
}

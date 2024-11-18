package com.COMPFIZ.core.actors;

import com.COMPFIZ.core.mixins.Constants;
import com.COMPFIZ.core.mixins.maths.Maths;
import com.COMPFIZ.core.models.Entity;
import com.COMPFIZ.core.attributes.Physics;
import org.joml.Vector3d;
import org.joml.Vector3f;


public class Gravitor {

    public void update(float interval, Entity planet, Entity[] masses) {
        Vector3d acc = new Vector3d();
        Vector3d force = new Vector3d();
        Vector3d[] gravitational = new Vector3d[masses.length];
        Physics stuff = (Physics) planet.physics;

        for (int i = 0; i < masses.length && masses[i] != null; i++){
            if(masses[i] == planet) continue;
            Vector3d d = planet.position.sub(masses[i].position, new Vector3d()).negate();
            if(d.length() == 0) continue;
            gravitational[i] = d.normalize(new Vector3d()).mul(Maths.calcOrbitForce(stuff.mass, ((Physics) masses[i].physics).mass, d.length()));
        }

        force.set(Maths.calcNetForces(gravitational));
        force.div(stuff.mass, acc);
        stuff.v.add(acc.mul(interval));
        System.out.println(force + " | " + stuff.name);
        System.out.println(acc + " \\ " + stuff.name);
    }
}

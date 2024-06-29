package nl.enjarai.cicada.util.duck;

import net.minecraft.util.math.Vec3d;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public interface ConvertibleVec3d {
    default Vector3d toVector3d() {
        throw new IllegalStateException();
    }

    default Vec3d fromVector3d(Vector3dc vector) {
        throw new IllegalStateException();
    }
}

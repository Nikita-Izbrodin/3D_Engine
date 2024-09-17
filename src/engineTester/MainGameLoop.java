package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {
    public static void main(String[] args) {

        DisplayManager.createDisplay();
        Loader loader = new Loader();


        RawModel model = OBJLoader.loadObjModel("dragon", loader);
        ModelTexture purple = new ModelTexture(loader.loadTexture("purple"));
        ModelTexture green = new ModelTexture(loader.loadTexture("green"));
        ModelTexture red = new ModelTexture(loader.loadTexture("red"));
        TexturedModel texturedModel = new TexturedModel(model, purple);
        purple.setShineDamper(10);
        purple.setReflectivity(1);
        TexturedModel texturedModel2 = new TexturedModel(model, green);
        green.setShineDamper(10);
        green.setReflectivity(1);
        TexturedModel texturedModel3 = new TexturedModel(model, red);
        red.setShineDamper(10);
        red.setReflectivity(1);

        Entity entity = new Entity(texturedModel, new Vector3f(0,-3,-40), 0, 0, 0, 1);
        Entity entity2 = new Entity(texturedModel2, new Vector3f(15,-3,-40), 0, 0, 0, 1);
        Entity entity3 = new Entity(texturedModel3, new Vector3f(-15,-3,-40), 0, 0, 0, 1);
        Light light = new Light(new Vector3f(200,200,100), new Vector3f(1,1,1));

        Camera camera = new Camera();

        MasterRenderer renderer = new MasterRenderer();

        while (!Display.isCloseRequested()) {
            entity.increaseRotation(0,0.5f,0);
            entity2.increaseRotation(0,0.5f,0);
            entity3.increaseRotation(0,0.5f,0);
            camera.move();
            renderer.processEntity(entity);
            renderer.processEntity(entity2);
            renderer.processEntity(entity3);
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}

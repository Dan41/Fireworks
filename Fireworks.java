package SmallProjectsAndGames;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Fireworks extends Application {
	
	Pane root = new Pane();
	
	Random rand = new Random();
	
	private Parent create() {
		root.setPrefSize(1000, 700);
		
		AnimationTimer timer = new AnimationTimer() {
			public void handle(long now) {
				if (rand.nextInt(20) + 1 == 1) {
					new Firework(root).update();
				}
			}
		};
		timer.start();
		
		return root;
	}
	
	private class Firework {
		
		Pane root;
		
		private boolean start = true;
		private double vel = rand.nextFloat() * 5 + 5;
		
		Circle firework = new Circle();
		
		public Firework(Pane root) {
			root.getChildren().add(firework);
			this.root = root;
		}
		
		private void update() {
			AnimationTimer motion = new AnimationTimer() {
				public void handle(long now) {
					if (start) {
						firework.setRadius(5);
						firework.setCenterX(rand.nextInt(1001));
						firework.setCenterY(700 + firework.getRadius());
					}
					start = false;
					
					firework.setCenterY(firework.getCenterY() - vel);
					
					vel -= 0.1;
					
					if (vel < 2) {
						this.stop();
						root.getChildren().remove(firework);
						new Particles(root, firework).movement();
						firework = null;
					}
				}
			};
			motion.start();
		}
		
	}
	
	private class Particles {
		
		private ArrayList<Circle> particles = new ArrayList<>();
		
		public Particles(Pane root, Circle firework) {
			
			for (int i = 0; i < 50; i++) {
				Circle particle = new Circle(firework.getCenterX(), firework.getCenterY(), 3);
				root.getChildren().add(particle);
				
				particles.add(particle);
			}
			
		}
		
		private void movement() {
			for (int i = 0; i < particles.size(); i++) {
				new Particle(particles.get(i)).update();
			}
		}
		
	}
	
	private class Particle {
		
		Circle particle;
		
		private float xVel = rand.nextFloat() * 6 - 3;
		private float yVel = rand.nextFloat() * 6 - 3;
		
		public Particle(Circle particle) {
			this.particle = particle;
		}
		
		private void update() {
			AnimationTimer move = new AnimationTimer() {
				public void handle(long now) {
					
					particle.setCenterX(particle.getCenterX() + xVel);
					particle.setCenterY(particle.getCenterY() + yVel);
					
					particle.setOpacity(particle.getOpacity() - 0.02);
					
					if (particle.getOpacity() == 0) {
						root.getChildren().remove(particle);
						particle = null;
					}
					
				}
			};
			move.start();
		}
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		Scene scene = new Scene(create());
		stage.setScene(scene);
		stage.setTitle("Fireworks");
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.photonvision.PhotonCamera;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import java.io.IOException;
import java.util.Optional;
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonUtils;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;
import java.util.List;
import java.util.ArrayList;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    
    XboxController js = new XboxController(0);
    
    PhotonCamera camera = new PhotonCamera("photonvision");
    var result = camera.getLatestResult();
    boolean hasTargets = result.hasTargets();
    List<PhotonTrackedTarget> targets = result.getTargets();
    PhotonTrackedTarget target = result.getBestTarget();
    double yaw = target.getYaw();
    double pitch = target.getPitch();
    double area = target.getArea();
    double skew = target.getSkew();
    List<TargetCorner> corners = target.getDetectedCorners();
    int targetId = target.getFiducialId();
    
    //yukar??s?? baz?? laz??m olabilicek de??i??kenleri falan tan??ml??yo
    //---------------------------------------------------------------------------------------------------------------------------------------------
    //a??a????s?? tam olarak ne bilmiyorum ama yapmaya ??al????t??????m??z bir ??ey olabilir
    // anlad??????m kadar??yla apriltag ile robot aras??ndaki mesafeyi, a????y??, ve target ve camera y??ksekli??ini kullanarak robotu targeta ayarl??yo
    
    /*if (js.getAButton()) {
      // Vision-alignment mode
      // Query the latest result from PhotonVision

      if (result.hasTargets()) {
          // First calculate range
          double range =
                  PhotonUtils.calculateDistanceToTargetMeters(
                          1,
                          1,   // de??erleri ??l??t??kten sonra d??zenle, sabit kamera ve target laz??m
                          90,
                          Units.degreesToRadians(result.getBestTarget().getPitch()));

          // Use this range as the measurement we give to the PID controller.
          // -1.0 required to ensure positive PID controller effort _increases_ range
          forwardSpeed = -controller.calculate(range, GOAL_RANGE_METERS); // de??erler ??l????ld??kten sonra atan??cak 
      }
      
    }*/
       //-------------------------------------------------------------------------------------------------------------------------------------------




  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}

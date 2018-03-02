/*
 * MCStatistics.java
 */

import com.comsol.model.*;
import com.comsol.model.util.*;

/** Model exported on Jan 17 2016, 15:39 by COMSOL 4.4.0.195. */
public class MCStatistics {

  public static void main(String[] args) {
    run();
  }

  public static Model run() {
    Model model = ModelUtil.create("Model");

    model.modelPath("C:\\Users\\plane\\Desktop\\COMSOL");

    model.name("MC Statistics.mph");

    model
         .comments("Hollow Cylinder\n\nIn this model of acoustic-structure interaction, pressure waves are generated from a point source or a line source inside a water-filled capped metal cylinder. The sound propagates through the cylinder and the surrounding water.");

    model.param().set("outerD", "29.98[cm]", "RF cavity outer diameter");
    model.param().set("outerR", "outerD/2.0");
    model.param().set("innerD", "28.44 [cm]", "RF cavity inner diameter");
    model.param().set("innerR", "innerD/2.0");
    model.param().set("endH", "1.37[cm]", "End cap height");
    model.param().set("cylinderH", "10.44[cm]", "Cylinder height");
    model.param().set("sourceR", "1.0 [mm]", "Radius of source region");
    model.param()
         .set("sourceRho", "2.0 [cm]", "Distance to source from z-axis");
    model.param().set("sourceAngle", "1 [deg]", "Source angle");
    model.param()
         .set("sourceX", "sourceRho * cos(sourceAngle)", "Source x position");
    model.param()
         .set("sourceY", "sourceRho * sin(sourceAngle)", "Source y position");
    model.param().set("sourceZ", "cylinderH/2", "Source z position");
    model.param().set("t0", "22 [us]", "Spark duration");
    model.param().set("sourceH", "1.0 [mm]");
    model.param().set("gamma", "3", "Spark decay rate");
    model.param().set("lambda", "50", "Cycles per t0");
    model.param().set("F0", "100 [N]", "Spark force magnitude");
    model.param().set("f0", "36^2", "Spark force density magnitude");
    model.param().set("t1", "0 [us]", "Upstream spark delay");
    model.param().set("t2", "t0 + t1", "Upstream spark end time");
    model.param()
         .set("E_Cu", "110e9 [Pa]", "Young's Modulus for Copper (110e9 [Pa])");
    model.param()
         .set("E_SS", "190e9 [Pa]", "Young's Modulus for 316 Stainless Steel (190e9 [Pa])");
    model.param().set("theta_0", "2.487 [rad]", "S0 angle");
    model.param().set("theta_5", "2.487 [rad]", "S5 angle");
    model.param().set("theta_1", "2.222 [rad]", "S1 angle");
    model.param().set("theta_2", "0.6581 [rad]", "S2 angle");
    model.param().set("theta_3", "5.236 [rad]", "S3 angle");
    model.param().set("theta_4", "3.927 [rad]", "S4 angle");
    model.param().set("A0", "1", "z pressure curve amplitude");
    model.param()
         .set("A1", "0.22", "z pressure curve amplitude at circumference");
    model.param().set("z01", "2.405", "first zero of J_0(x)");
    model.param().set("omega", "2*pi*805e6 [Hz]", "Angular frequency");
    model.param().set("A2", "0.22");
    model.param().set("A3", "3.9");
    model.param().set("fill_pressure", "5.515e6 [Pa]", "Fill gas pressure");
    model.param()
         .set("air_pressure", "1.01325e5 [Pa]", "Ambient air pressure (1 ATM)");
    model.param().set("bolt_pressure", "1.0e7 [N/m^2]");
    model.param().set("A_r", "1e6 [Pa]", "Radial shock pressure amplitude");

    model.modelNode().create("mod1");
    model.modelNode("mod1").name("Model 1");

    model.func().create("pw2", "Piecewise");
    model.func().create("pw8", "Piecewise");
    model.func().create("pw7", "Piecewise");
    model.func().create("pw10", "Piecewise");
    model.func().create("pw12", "Piecewise");
    model.func().create("pw13", "Piecewise");
    model.func().create("pw9", "Piecewise");
    model.func().create("an4", "Analytic");
    model.func().create("an7", "Analytic");
    model.func().create("an8", "Analytic");
    model.func().create("pw6", "Piecewise");
    model.func().create("pw3", "Piecewise");
    model.func().create("pw5", "Piecewise");
    model.func().create("an3", "Analytic");
    model.func().create("an1", "Analytic");
    model.func().create("an2", "Analytic");
    model.func().create("pw4", "Piecewise");
    model.func().create("an5", "Analytic");
    model.func().create("an6", "Analytic");
    model.func().create("pw11", "Piecewise");
    model.func().create("an9", "Analytic");
    model.func().create("an10", "Analytic");
    model.func().create("an11", "Analytic");
    model.func().create("an16", "Analytic");
    model.func().create("an15", "Analytic");
    model.func().create("pw14", "Piecewise");
    model.func().create("an13", "Analytic");
    model.func().create("an17", "Analytic");
    model.func().create("an18", "Analytic");
    model.func().create("pw15", "Piecewise");
    model.func().create("gp1", "GaussianPulse");
    model.func().create("an19", "Analytic");
    model.func("pw2").model("mod1");
    model.func("pw2").active(false);
    model.func("pw2")
         .set("pieces", new String[][]{{"t1", "t2", "F0*exp(-gamma*(t-t1)/t0)*sin(4*pi/(1+t0/(t-t1)))"}});
    model.func("pw2").set("argunit", "s");
    model.func("pw2").set("fununit", "Pa");
    model.func("pw2").set("funcname", "USpark");
    model.func("pw2").set("arg", "t");
    model.func("pw8").model("mod1");
    model.func("pw8")
         .set("pieces", new String[][]{{"0", "t0/3", "log(1+3*(exp(1)-1)/t0*t)"}, {"t0/3", "3*t0/4", "1"}, {"3*t0/4", "t0", "-log(1-4*(1-exp(-1)) + 4*(1-exp(-1))/t0*t)"}});
    model.func("pw8").set("argunit", "s");
    model.func("pw8").set("funcname", "RFEnvelope");
    model.func("pw8").set("arg", "t");
    model.func("pw7").model("mod1");
    model.func("pw7").active(false);
    model.func("pw7")
         .set("pieces", new String[][]{{"0", "2.54", "-0.36*rho+1.052"}, {"2.54", "2.6", "-2.17*rho+5.64"}, {"2.6", "4.0", "2.94e-2*rho-7.64e-2"}, {"4.0", "11.43", "4.114e-2*besselj(0, 0.324*(rho-4.0))"}});
    model.func("pw7").set("argunit", "cm");
    model.func("pw7").set("fununit", "MV/m");
    model.func("pw7").set("funcname", "EField");
    model.func("pw7").set("arg", "rho");
    model.func("pw10").model("mod1");
    model.func("pw10").active(false);
    model.func("pw10")
         .set("pieces", new String[][]{{"0", "buttonR", "A0*(besselj(0, z01/buttonR*rho)-1)"}, {"buttonR", "innerR", "(A0-A1)*(1/log(100*exp(1)-99)*log(1+100*(exp(1)-1)/(innerR-buttonR)*(rho-buttonR))-1)+A1"}});
    model.func("pw10").set("argunit", "m");
    model.func("pw10").set("funcname", "ZForce");
    model.func("pw10").set("arg", "rho");
    model.func("pw12").model("mod1");
    model.func("pw12").active(false);
    model.func("pw12")
         .set("pieces", new String[][]{{"-4.05", "4.05", "A2*(1./sqrt(2*pi*1.6^2)*exp(-z^2/(2*1.6^2))-116.66)"}});
    model.func("pw12").set("argunit", "cm");
    model.func("pw12").set("smooth", "cont");
    model.func("pw12").set("funcname", "RcForce");
    model.func("pw12").set("arg", "z");
    model.func("pw13").model("mod1");
    model.func("pw13").active(false);
    model.func("pw13")
         .set("pieces", new String[][]{{"-4.05", "4.05", "A3*(1./sqrt(2*pi*1.3^2)*exp(-z^2/(2*1.3^2))-0.26)"}});
    model.func("pw13").set("argunit", "cm");
    model.func("pw13").set("smooth", "cont");
    model.func("pw13").set("funcname", "RbForce");
    model.func("pw13").set("arg", "z");
    model.func("pw9").model("mod1");
    model.func("pw9").active(false);
    model.func("pw9")
         .set("pieces", new String[][]{{"0", "t0", "cos(omega*t)"}});
    model.func("pw9").set("argunit", "s");
    model.func("pw9").set("funcname", "RFModulation");
    model.func("pw9").set("arg", "t");
    model.func("an4").model("mod1");
    model.func("an4").active(false);
    model.func("an4").set("args", new String[]{"t", "rho"});
    model.func("an4").set("argunit", "s, m");
    model.func("an4")
         .set("expr", "f0*RFEnvelope(t)*abs(RFModulation(t))*ZForce(rho)");
    model.func("an4")
         .set("plotargs", new String[][]{{"t", "0", "1"}, {"rho", "0", "innerR"}});
    model.func("an4").set("funcname", "RFHammerZ");
    model.func("an4").set("fununit", "N/m^2");
    model.func("an7").model("mod1");
    model.func("an7").active(false);
    model.func("an7").set("args", new String[]{"t", "z"});
    model.func("an7").set("argunit", "s, m");
    model.func("an7")
         .set("expr", "f0*RFEnvelope(t)*abs(RFModulation(t))*RcForce(z)");
    model.func("an7")
         .set("plotargs", new String[][]{{"t", "0", "1"}, {"z", "-cylinderH/2", "cylinderH/2"}});
    model.func("an7").set("funcname", "RFHammerRc");
    model.func("an7").set("fununit", "N/m^2");
    model.func("an8").model("mod1");
    model.func("an8").active(false);
    model.func("an8").set("args", new String[]{"t", "z"});
    model.func("an8").set("argunit", "s, cm");
    model.func("an8")
         .set("expr", "RFEnvelope(t)*abs(RFModulation(t))*RbForce(z)");
    model.func("an8")
         .set("plotargs", new String[][]{{"t", "0", "1"}, {"z", "-4.05", "4.05"}});
    model.func("an8").set("funcname", "RFHammerRb");
    model.func("an8").set("fununit", "N/m^2");
    model.func("pw6").model("mod1");
    model.func("pw6").active(false);
    model.func("pw6")
         .set("pieces", new String[][]{{"0", "t0/4", "F0*log(1+4*(exp(1)-1)/t0*t)*sin(2*pi*lambda/t0*t)"}, {"t0/4", "3*t0/4", "F0*sin(2*pi*lambda/t0*t)"}, {"3*t0/4", "t0", "-F0*log(1-4*(1-exp(-1))+4*(1-exp(-1))/t0*t)*sin(2*pi*lambda/t0*t)"}});
    model.func("pw6").set("argunit", "s");
    model.func("pw6").set("fununit", "N");
    model.func("pw6").set("funcname", "LogSinSpark");
    model.func("pw6").set("arg", "t");
    model.func("pw3").model("mod1");
    model.func("pw3").active(false);
    model.func("pw3")
         .set("pieces", new String[][]{{"0", "t0", "F0*exp(-gamma/t0*t)*sin(2*pi*lambda/t0*t)"}});
    model.func("pw3").set("argunit", "s");
    model.func("pw3").set("fununit", "N");
    model.func("pw3").set("funcname", "DSpark");
    model.func("pw3").set("arg", "t");
    model.func("pw5").model("mod1");
    model.func("pw5").active(false);
    model.func("pw5")
         .set("pieces", new String[][]{{"1.7*t0", "2.7*t0", "-F0*exp(-gamma/t0*(t-1.7*t0))*sin(4*pi/(t0/t))"}});
    model.func("pw5").set("argunit", "s");
    model.func("pw5").set("fununit", "N");
    model.func("pw5").set("funcname", "ESpark");
    model.func("pw5").set("arg", "t");
    model.func("an3").model("mod1");
    model.func("an3").active(false);
    model.func("an3").set("args", new String[]{"x", "y", "z"});
    model.func("an3").set("argunit", "mm");
    model.func("an3").set("expr", "sqrt(x^2+y^2+z^2)");
    model.func("an3")
         .set("plotargs", new String[][]{{"x", "-200", "200"}, {"y", "-200", "200"}, {"z", "-200", "200"}});
    model.func("an3").set("funcname", "r");
    model.func("an3").set("fununit", "mm");
    model.func("an1").model("mod1");
    model.func("an1").active(false);
    model.func("an1").set("args", new String[]{"x", "y"});
    model.func("an1").set("argunit", "mm");
    model.func("an1").set("expr", "atan2(y, x)");
    model.func("an1")
         .set("plotargs", new String[][]{{"x", "-200", "200"}, {"y", "-200", "200"}});
    model.func("an1").set("funcname", "theta");
    model.func("an1").set("fununit", "mm");
    model.func("an2").model("mod1");
    model.func("an2").active(false);
    model.func("an2").set("args", new String[]{"x", "y", "z"});
    model.func("an2").set("argunit", "mm");
    model.func("an2").set("expr", "acos(z/r(x, y, z))");
    model.func("an2")
         .set("plotargs", new String[][]{{"x", "-200", "200"}, {"y", "-200", "200"}, {"z", "-200", "200"}});
    model.func("an2").set("funcname", "phi");
    model.func("an2").set("fununit", "mm");
    model.func("pw4").model("mod1");
    model.func("pw4").active(false);
    model.func("pw4")
         .set("pieces", new String[][]{{"0", "t0", "F0*exp(-gamma/t0*t)*sin(4*pi/(1+t0/t))"}});
    model.func("pw4").set("argunit", "s");
    model.func("pw4").set("fununit", "N");
    model.func("pw4").set("funcname", "OldDSpark");
    model.func("pw4").set("arg", "t");
    model.func("an5").model("mod1");
    model.func("an5").active(false);
    model.func("an5").set("args", new String[]{"z"});
    model.func("an5").set("argunit", "m");
    model.func("an5")
         .set("expr", "f0*RFEnvelope(20[us])*abs(RFModulation(20[us]))*RbForce(z)");
    model.func("an5")
         .set("plotargs", new String[][]{{"z", "-4.05e-2 [m]", "4.05e-2 [m]"}});
    model.func("an5").set("fununit", "N/m^2");
    model.func("an6").model("mod1");
    model.func("an6").active(false);
    model.func("an6").set("args", new String[]{"rho"});
    model.func("an6").set("argunit", "m");
    model.func("an6").set("expr", "RFHammerZ(20[us], rho)");
    model.func("an6").set("plotargs", new String[][]{{"rho", "0", "innerR"}});
    model.func("an6").set("fununit", "N/m^2");
    model.func("pw11").model("mod1");
    model.func("pw11").active(false);
    model.func("pw11")
         .set("pieces", new String[][]{{"0", "innerR", "A0*rho"}});
    model.func("pw11").set("argunit", "m");
    model.func("pw11").set("fununit", "MV/m");
    model.func("pw11").set("arg", "rho");
    model.func("an9").model("mod1");
    model.func("an9")
         .set("args", new String[]{"x", "a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", 
         "a9", "a10", "a11", "a12"});
    model.func("an9")
         .set("expr", "a0+a1*x+a2*x^2+a3*x^3+a4*x^4+a5*x^5+a6*x^6+a7*x^7+a8*x^8+a9*x^9+a10*x^10+a11*x^11+a12*x^12");
    model.func("an9")
         .set("plotargs", new String[][]{{"x", "0", "11.43"}, {"a0", "3.53795685171", "3.53795685171"}, {"a1", "-0.732040540186", "-0.732040540186"}, {"a2", "0.040534041787", "0.040534041787"}, {"a3", "-1.67416630335", "-1.67416630335"}, {"a4", "1.8462424817", "1.8462424817"}, {"a5", "-0.937002855303", "-0.937002855303"}, {"a6", "0.282243837285", "0.282243837285"}, {"a7", "-0.0549768592192", "-0.0549768592192"}, {"a8", "0.00713032109955", "0.00713032109955"}, 
         {"a9", "-0.000613104803025", "-0.000613104803025"}, {"a10", "3.36166739576e-05", "3.36166739576e-05"}, {"a11", "-1.06499968921e-06", "-1.06499968921e-06"}, {"a12", "1.48424337251e-08", "1.48424337251e-08"}});
    model.func("an9").set("funcname", "polynomial");
    model.func("an10").model("mod1");
    model.func("an10").set("args", new String[]{"rho"});
    model.func("an10").set("argunit", "cm");
    model.func("an10")
         .set("expr", "polynomial(rho,0.00368184757967,0.00248141494311,-0.00236272719838,-0.00823606487738,-0.00400237078478,0.0108362218278,-0.00597060284391,0.00164930222627,-0.000269311153226,2.72388272413e-05,-1.68301049632e-06,5.83778786552e-08,-8.72965653923e-10)");
    model.func("an10")
         .set("plotargs", new String[][]{{"rho", "buttonR", "innerR"}});
    model.func("an10").set("funcname", "f_E_w");
    model.func("an10").set("fununit", "N/m^2");
    model.func("an11").model("mod1");
    model.func("an11").set("args", new String[]{"z"});
    model.func("an11").set("argunit", "cm");
    model.func("an11")
         .set("expr", "polynomial(z,2.8713664096e-06,1.24757082135e-05,2.41075218593e-05,2.55938540794e-05,1.51250056606e-05,3.6804377726e-06,-1.24862106283e-06,-1.4006737171e-06,-5.602897482e-07,-1.28409640849e-07,-1.76883103617e-08,-1.36705558825e-09,-4.57273601159e-11)");
    model.func("an11")
         .set("plotargs", new String[][]{{"z", "-4.8024e-2", "-0.4176e-2"}});
    model.func("an11").set("funcname", "f_E_c_neg");
    model.func("an11").set("fununit", "N/m^2");
    model.func("an16").model("mod1");
    model.func("an16").set("args", new String[]{"z"});
    model.func("an16").set("argunit", "cm");
    model.func("an16")
         .set("expr", "polynomial(z,5.75629051949e-08,0,2.56432745631e-06,0,0,0,0,0,0,0,0,0,0)");
    model.func("an16")
         .set("plotargs", new String[][]{{"z", "-0.4176e-2", "0.4176e-2"}});
    model.func("an16").set("funcname", "f_E_c_mid");
    model.func("an16").set("fununit", "N/m^2");
    model.func("an15").model("mod1");
    model.func("an15").set("args", new String[]{"z"});
    model.func("an15").set("argunit", "cm");
    model.func("an15")
         .set("expr", "polynomial(z,-3.29098191999e-08,3.14430285686e-06,-1.40317588598e-05,2.93014161365e-05,-3.64159611941e-05,2.95291151681e-05,-1.63208594713e-05,6.26552067548e-06,-1.67138241446e-06,3.03993720189e-07,-3.59601814332e-08,2.49413111681e-09,-7.69661345643e-11)");
    model.func("an15")
         .set("plotargs", new String[][]{{"z", "0.4176e-2", "4.8024e-2"}});
    model.func("an15").set("funcname", "f_E_c_pos");
    model.func("an15").set("fununit", "N/m^2");
    model.func("pw14").model("mod1");
    model.func("pw14")
         .set("pieces", new String[][]{{"-5.22", "-4.8024", "0"}, {"-4.8024", "-0.4176", "f_E_c_neg(z)"}, {"-0.4176", "0.4176", "f_E_c_mid(z)"}, {"0.4176", "4.8024", "f_E_c_pos(z)"}, {"4.8024", "5.22", "0"}});
    model.func("pw14").set("argunit", "cm");
    model.func("pw14").set("fununit", "N/m^2");
    model.func("pw14").set("funcname", "f_E_c");
    model.func("pw14").set("arg", "z");
    model.func("an13").model("mod1");
    model.func("an13").set("args", new String[]{"rho"});
    model.func("an13").set("argunit", "cm");
    model.func("an13")
         .set("expr", "polynomial(rho,-1.53366263015,1.135603284,-0.374147422109,0.0657247242586,-0.0064374766562,0.000332337035276,-7.04858247012e-06,0,0,0,0,0,0)");
    model.func("an13")
         .set("plotargs", new String[][]{{"rho", "buttonR", "innerR"}});
    model.func("an13").set("funcname", "f_H_w");
    model.func("an13").set("fununit", "N/m^2");
    model.func("an17").model("mod1");
    model.func("an17").set("args", new String[]{"z"});
    model.func("an17").set("argunit", "cm");
    model.func("an17")
         .set("expr", "polynomial(z,-1.19148790806,0.000305445381988,0.000176416888301,5.74976798982e-05,9.51251388037e-06,6.16329181025e-07,0,0,0,0,0,0,0)");
    model.func("an17")
         .set("plotargs", new String[][]{{"z", "-4.8024e-2", "0"}});
    model.func("an17").set("funcname", "f_H_c_neg");
    model.func("an17").set("fununit", "N/m^2");
    model.func("an18").model("mod1");
    model.func("an18").set("args", new String[]{"z"});
    model.func("an18").set("argunit", "cm");
    model.func("an18")
         .set("expr", "polynomial(z,-1.19153546669,-0.000267258019278,0.000170435077077,-6.16417787834e-05,1.10989001152e-05,-7.68786664707e-07,0,0,0,0,0,0,0)");
    model.func("an18")
         .set("plotargs", new String[][]{{"z", "0", "4.8024e-2"}});
    model.func("an18").set("funcname", "f_H_c_pos");
    model.func("an18").set("fununit", "N/m^2");
    model.func("pw15").model("mod1");
    model.func("pw15")
         .set("pieces", new String[][]{{"-5.22", "-4.8024", "-1.19177"}, {"-4.8024", "0", "f_H_c_neg(z)"}, {"0", "4.8024", "f_H_c_pos(z)"}, {"4.8024", "5.22", "-1.191775"}});
    model.func("pw15").set("argunit", "cm");
    model.func("pw15").set("fununit", "N/m^2");
    model.func("pw15").set("funcname", "f_H_c");
    model.func("pw15").set("arg", "z");
    model.func("gp1").model("mod1");
    model.func("gp1").set("sigma", "0.5e-6");
    model.func("gp1").set("location", "t0");
    model.func("an19").model("mod1");
    model.func("an19").set("args", new String[]{"t"});
    model.func("an19").set("argunit", "s");
    model.func("an19").set("expr", "A_r / 8.0e5 * gp1(t/(1 [s]))");
    model.func("an19")
         .set("plotargs", new String[][]{{"t", "18e-6", "26e-6"}});
    model.func("an19").set("funcname", "shock");
    model.func("an19").set("fununit", "Pa");

    model.geom().create("geom1", 3);
    model.geom("geom1").lengthUnit("mm");
    model.geom("geom1").geomRep("comsol");
    model.geom("geom1").feature().create("cyl68", "Cylinder");
    model.geom("geom1").feature().create("cyl2", "Cylinder");
    model.geom("geom1").feature().create("dif1", "Difference");
    model.geom("geom1").feature().create("cyl69", "Cylinder");
    model.geom("geom1").feature("cyl68").name("Outer Cylinder");
    model.geom("geom1").feature("cyl68").set("r", "outerD/2");
    model.geom("geom1").feature("cyl68")
         .set("pos", new String[]{"0", "0", "-(cylinderH/2+endH)"});
    model.geom("geom1").feature("cyl68").set("h", "cylinderH+2*endH");
    model.geom("geom1").feature("cyl2").name("Resonator Chamber");
    model.geom("geom1").feature("cyl2").set("r", "innerD/2");
    model.geom("geom1").feature("cyl2")
         .set("pos", new String[]{"0", "0", "-cylinderH/2"});
    model.geom("geom1").feature("cyl2").set("h", "cylinderH");
    model.geom("geom1").feature("dif1").selection("input")
         .set(new String[]{"cyl68"});
    model.geom("geom1").feature("dif1").selection("input2")
         .set(new String[]{"cyl2"});
    model.geom("geom1").feature("cyl69").name("Source");
    model.geom("geom1").feature("cyl69").set("r", "sourceR");
    model.geom("geom1").feature("cyl69")
         .set("pos", new String[]{"sourceX", "sourceY", "-cylinderH/2-endH/2"});
    model.geom("geom1").feature("cyl69").set("h", "endH/2");
    model.geom("geom1").run();

    model.view().create("view2", 2);
    model.view().create("view3", 2);

    model.material().create("mat1");
    model.material().create("mat2");
    model.material("mat2").propertyGroup()
         .create("Enu", "Young's modulus and Poisson's ratio");
    model.material("mat2").propertyGroup()
         .create("linzRes", "Linearized resistivity");
    model.material("mat2").selection().set(new int[]{1, 2});

    model.physics().create("solid", "SolidMechanics", "geom1");
    model.physics("solid").feature("lemm1").feature()
         .create("dmp1", "Damping", 3);
    model.physics("solid").feature("lemm1").feature()
         .create("iss1", "InitialStressandStrain", 3);
    model.physics("solid").feature("lemm1").feature("iss1").selection()
         .set(new int[]{});
    model.physics("solid").feature().create("bndl4", "BoundaryLoad", 2);
    model.physics("solid").feature("bndl4").selection()
         .set(new int[]{9, 10, 13, 14});
    model.physics("solid").feature("bndl4").feature()
         .create("ph1", "Phase", 2);
    model.physics().create("solid2", "SolidMechanics", "geom1");
    model.physics("solid2").feature("lemm1").feature()
         .create("dmp1", "Damping", 3);
    model.physics("solid2").feature().create("bndl1", "BoundaryLoad", 2);
    model.physics("solid2").feature("bndl1").selection().set(new int[]{8});
    model.physics("solid2").feature().create("bndl2", "BoundaryLoad", 2);
    model.physics("solid2").feature("bndl2").selection()
         .set(new int[]{5, 6, 16, 17});
    model.physics("solid2").feature().create("bndl3", "BoundaryLoad", 2);
    model.physics("solid2").feature("bndl3").selection()
         .set(new int[]{9, 10, 13, 14});

    model.mesh().create("mesh1", "geom1");
    model.mesh("mesh1").feature().create("swe1", "Sweep");
    model.mesh("mesh1").feature().create("swe2", "Sweep");
    model.mesh("mesh1").feature().create("ftet1", "FreeTet");
    model.mesh("mesh1").feature("swe1").selection().geom("geom1", 3);
    model.mesh("mesh1").feature("swe2").selection().geom("geom1", 3);
    model.mesh("mesh1").feature("ftet1").selection().geom("geom1");
    model.mesh("mesh1").feature("ftet1").feature().create("size12", "Size");
    model.mesh("mesh1").feature("ftet1").feature().create("size9", "Size");
    model.mesh("mesh1").feature("ftet1").feature().create("size6", "Size");
    model.mesh("mesh1").feature("ftet1").feature().create("size3", "Size");
    model.mesh("mesh1").feature("ftet1").feature().create("size4", "Size");
    model.mesh("mesh1").feature("ftet1").feature().create("size7", "Size");
    model.mesh("mesh1").feature("ftet1").feature().create("size8", "Size");
    model.mesh("mesh1").feature("ftet1").feature().create("size5", "Size");
    model.mesh("mesh1").feature("ftet1").feature().create("size11", "Size");
    model.mesh("mesh1").feature("ftet1").feature("size12").selection()
         .geom("geom1", 3);
    model.mesh("mesh1").feature("ftet1").feature("size9").selection()
         .geom("geom1", 3);
    model.mesh("mesh1").feature("ftet1").feature("size6").selection()
         .geom("geom1", 3);
    model.mesh("mesh1").feature("ftet1").feature("size3").selection()
         .geom("geom1", 3);
    model.mesh("mesh1").feature("ftet1").feature("size4").selection()
         .geom("geom1", 3);
    model.mesh("mesh1").feature("ftet1").feature("size7").selection()
         .geom("geom1", 3);

    model.view("view1").set("transparency", "on");
    model.view("view2").axis().set("xmin", "-2.557544708251953");
    model.view("view2").axis().set("xmax", "2.557544708251953");
    model.view("view3").axis().set("xmin", "-0.46042898297309875");
    model.view("view3").axis().set("ymin", "-0.027777746319770813");
    model.view("view3").axis().set("xmax", "0.9604289531707764");
    model.view("view3").axis().set("ymax", "0.5277777314186096");

    model.material("mat1").name("304 Stainless Steel");
    model.material("mat1").propertyGroup("def").set("density", "8.03e3");
    model.material("mat1").propertyGroup("def").set("soundspeed", "5700");
    model.material("mat1").propertyGroup("def")
         .set("relpermeability", new String[]{"1", "0", "0", "0", "1", "0", "0", "0", "1"});
    model.material("mat1").propertyGroup("def")
         .set("electricconductivity", new String[]{"1.42e6", "0", "0", "0", "1.42e6", "0", "0", "0", "1.42e6"});
    model.material("mat1").propertyGroup("def")
         .set("thermalexpansioncoefficient", new String[]{"1.6e-5", "0", "0", "0", "1.6e-5", "0", "0", "0", "1.6e-5"});
    model.material("mat1").propertyGroup("def").set("heatcapacity", "500");
    model.material("mat1").propertyGroup("def")
         .set("relpermittivity", new String[]{"1", "0", "0", "0", "1", "0", "0", "0", "1"});
    model.material("mat1").propertyGroup("def")
         .set("thermalconductivity", new String[]{"16.2", "0", "0", "0", "16.2", "0", "0", "0", "16.2"});
    model.material("mat1").propertyGroup("def").set("youngsmodulus", "193e9");
    model.material("mat1").propertyGroup("def").set("poissonsratio", "0.30");
    model.material("mat1").propertyGroup("def").set("lossfactor", ".04");
    model.material("mat2").name("Copper");
    model.material("mat2").propertyGroup("def")
         .set("relpermeability", new String[]{"1", "0", "0", "0", "1", "0", "0", "0", "1"});
    model.material("mat2").propertyGroup("def")
         .set("electricconductivity", new String[]{"5.998e7[S/m]", "0", "0", "0", "5.998e7[S/m]", "0", "0", "0", "5.998e7[S/m]"});
    model.material("mat2").propertyGroup("def")
         .set("thermalexpansioncoefficient", new String[]{"17e-6[1/K]", "0", "0", "0", "17e-6[1/K]", "0", "0", "0", "17e-6[1/K]"});
    model.material("mat2").propertyGroup("def")
         .set("heatcapacity", "385[J/(kg*K)]");
    model.material("mat2").propertyGroup("def")
         .set("relpermittivity", new String[]{"1", "0", "0", "0", "1", "0", "0", "0", "1"});
    model.material("mat2").propertyGroup("def")
         .set("density", "8700[kg/m^3]");
    model.material("mat2").propertyGroup("def")
         .set("thermalconductivity", new String[]{"400[W/(m*K)]", "0", "0", "0", "400[W/(m*K)]", "0", "0", "0", "400[W/(m*K)]"});
    model.material("mat2").propertyGroup("Enu")
         .set("youngsmodulus", "110e9[Pa]");
    model.material("mat2").propertyGroup("Enu").set("poissonsratio", "0.35");
    model.material("mat2").propertyGroup("linzRes").set("rho0", "");
    model.material("mat2").propertyGroup("linzRes").set("alpha", "");
    model.material("mat2").propertyGroup("linzRes").set("Tref", "");
    model.material("mat2").propertyGroup("linzRes")
         .set("rho0", "1.72e-8[ohm*m]");
    model.material("mat2").propertyGroup("linzRes")
         .set("alpha", "0.0039[1/K]");
    model.material("mat2").propertyGroup("linzRes").set("Tref", "298[K]");
    model.material("mat2").propertyGroup("linzRes").addInput("temperature");

    model.physics("solid").prop("EquationForm").set("form", "Frequency");
    model.physics("solid").feature("lemm1").feature("dmp1")
         .set("beta_dK", "1e-12");
    model.physics("solid").feature("lemm1").feature("dmp1").active(false);
    model.physics("solid").feature("lemm1").feature("iss1").active(false);
    model.physics("solid").feature("bndl4")
         .set("FperArea", new String[][]{{"0"}, {"0"}, {"1e6"}});
    model.physics("solid").feature("bndl4").set("coordinateSystem", "sys1");
    model.physics("solid").feature("bndl4").name("Spark");
    model.physics("solid2").prop("EquationForm").set("form", "Transient");
    model.physics("solid2").feature("lemm1").feature("dmp1")
         .set("beta_dK", "1e-8");
    model.physics("solid2").feature("bndl1")
         .set("FperArea", new String[][]{{"0"}, {"0"}, {"493*RFEnvelope(t)*(f_E_w(sqrt(x^2+y^2)) + f_H_w(sqrt(x^2+y^2)))"}});
    model.physics("solid2").feature("bndl1").set("coordinateSystem", "sys1");
    model.physics("solid2").feature("bndl1").active(false);
    model.physics("solid2").feature("bndl1").name("End Flange RF Hammer");
    model.physics("solid2").feature("bndl2")
         .set("FperArea", new String[][]{{"0"}, {"0"}, {"493*RFEnvelope(t)*(f_E_c(z) + f_H_c(z))"}});
    model.physics("solid2").feature("bndl2").set("coordinateSystem", "sys1");
    model.physics("solid2").feature("bndl2").active(false);
    model.physics("solid2").feature("bndl2").name("Center Flange RF Hammer");
    model.physics("solid2").feature("bndl3")
         .set("FperArea", new String[][]{{"0"}, {"0"}, {"shock(t)"}});
    model.physics("solid2").feature("bndl3").set("coordinateSystem", "sys1");
    model.physics("solid2").feature("bndl3").name("Shock");

    model.mesh("mesh1").run();

    model.study().create("std1");
    model.study("std1").feature().create("freq", "Frequency");
    model.study().create("std2");
    model.study("std2").feature().create("eig", "Eigenfrequency");
    model.study().create("std3");
    model.study("std3").feature().create("param", "Parametric");
    model.study("std3").feature().create("time", "Transient");

    model.sol().create("sol1");
    model.sol("sol1").study("std2");
    model.sol("sol1").attach("std2");
    model.sol("sol1").feature().create("st1", "StudyStep");
    model.sol("sol1").feature().create("v1", "Variables");
    model.sol("sol1").feature().create("e1", "Eigenvalue");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol().create("sol2");
    model.sol("sol2").study("std1");
    model.sol("sol2").attach("std1");
    model.sol("sol2").feature().create("st1", "StudyStep");
    model.sol("sol2").feature().create("v1", "Variables");
    model.sol("sol2").feature().create("s1", "Stationary");
    model.sol("sol2").feature("s1").feature().create("p1", "Parametric");
    model.sol("sol2").feature("s1").feature().create("fc1", "FullyCoupled");
    model.sol("sol2").feature("s1").feature().remove("fcDef");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol().create("sol3");
    model.sol("sol3").study("std3");
    model.sol("sol3").attach("std3");
    model.sol("sol3").feature().create("st1", "StudyStep");
    model.sol("sol3").feature().create("v1", "Variables");
    model.sol("sol3").feature().create("t1", "Time");
    model.sol("sol3").feature("t1").feature().create("fc1", "FullyCoupled");
    model.sol("sol3").feature("t1").feature().remove("fcDef");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol().create("sol4");
    model.sol("sol4").study("std3");
    model.sol("sol4").feature().create("su1", "StoreSolution");
    model.sol("sol4").feature().create("su2", "StoreSolution");
    model.sol("sol4").feature().create("su3", "StoreSolution");
    model.sol("sol4").feature().create("su4", "StoreSolution");
    model.sol("sol4").feature().create("su5", "StoreSolution");
    model.sol("sol4").feature().create("su6", "StoreSolution");
    model.sol("sol4").feature().create("su7", "StoreSolution");
    model.sol("sol4").feature().create("su8", "StoreSolution");
    model.sol("sol4").feature().create("su9", "StoreSolution");
    model.sol("sol4").feature().create("su10", "StoreSolution");
    model.sol("sol4").feature().create("su11", "StoreSolution");
    model.sol("sol4").feature().create("su12", "StoreSolution");
    model.sol("sol4").feature().create("su13", "StoreSolution");
    model.sol("sol4").feature().create("su14", "StoreSolution");
    model.sol("sol4").feature().create("su15", "StoreSolution");
    model.sol("sol4").feature().create("su16", "StoreSolution");
    model.sol("sol4").feature().create("su17", "StoreSolution");
    model.sol("sol4").feature().create("su18", "StoreSolution");
    model.sol("sol4").feature().create("su19", "StoreSolution");
    model.sol("sol4").feature().create("su20", "StoreSolution");
    model.sol("sol4").feature().create("su21", "StoreSolution");
    model.sol("sol4").feature().create("su22", "StoreSolution");
    model.sol("sol4").feature().create("su23", "StoreSolution");
    model.sol("sol4").feature().create("su24", "StoreSolution");
    model.sol("sol4").feature().create("su25", "StoreSolution");
    model.sol("sol4").feature().create("su26", "StoreSolution");
    model.sol("sol4").feature().create("su27", "StoreSolution");
    model.sol("sol4").feature().create("su28", "StoreSolution");
    model.sol("sol4").feature().create("su29", "StoreSolution");
    model.sol("sol4").feature().create("su30", "StoreSolution");
    model.sol("sol4").feature().create("su31", "StoreSolution");
    model.sol("sol4").feature().create("su32", "StoreSolution");
    model.sol("sol4").feature().create("su33", "StoreSolution");
    model.sol("sol4").feature().create("su34", "StoreSolution");
    model.sol("sol4").feature().create("su35", "StoreSolution");
    model.sol("sol4").feature().create("su36", "StoreSolution");
    model.sol("sol4").feature().create("su37", "StoreSolution");
    model.sol("sol4").feature().create("su38", "StoreSolution");
    model.sol("sol4").feature().create("su39", "StoreSolution");
    model.sol("sol4").feature().create("su40", "StoreSolution");
    model.sol("sol4").feature().create("su41", "StoreSolution");
    model.sol("sol4").feature().create("su42", "StoreSolution");
    model.sol("sol4").feature().create("su43", "StoreSolution");
    model.sol("sol4").feature().create("su44", "StoreSolution");
    model.sol("sol4").feature().create("su45", "StoreSolution");
    model.sol("sol4").feature().create("su46", "StoreSolution");
    model.sol("sol4").feature().create("su47", "StoreSolution");
    model.sol("sol4").feature().create("su48", "StoreSolution");
    model.sol("sol4").feature().create("su49", "StoreSolution");
    model.sol("sol4").feature().create("su50", "StoreSolution");
    model.sol("sol4").feature().create("su51", "StoreSolution");
    model.sol("sol4").feature().create("su52", "StoreSolution");
    model.sol("sol4").feature().create("su53", "StoreSolution");
    model.sol("sol4").feature().create("su54", "StoreSolution");
    model.sol("sol4").feature().create("su55", "StoreSolution");
    model.sol("sol4").feature().create("su56", "StoreSolution");
    model.sol("sol4").feature().create("su57", "StoreSolution");
    model.sol("sol4").feature().create("su58", "StoreSolution");
    model.sol("sol4").feature().create("su59", "StoreSolution");
    model.sol("sol4").feature().create("su60", "StoreSolution");
    model.sol("sol4").feature().create("su61", "StoreSolution");
    model.sol("sol4").feature().create("su62", "StoreSolution");
    model.sol("sol4").feature().create("su63", "StoreSolution");
    model.sol("sol4").feature().create("su64", "StoreSolution");
    model.sol("sol4").feature().create("su65", "StoreSolution");
    model.sol("sol4").feature().create("su66", "StoreSolution");
    model.sol("sol4").feature().create("su67", "StoreSolution");
    model.sol("sol4").feature().create("su68", "StoreSolution");
    model.sol("sol4").feature().create("su69", "StoreSolution");
    model.sol("sol4").feature().create("su70", "StoreSolution");
    model.sol("sol4").feature().create("su71", "StoreSolution");
    model.sol("sol4").feature().create("su72", "StoreSolution");
    model.sol("sol4").feature().create("su73", "StoreSolution");
    model.sol("sol4").feature().create("su74", "StoreSolution");
    model.sol("sol4").feature().create("su75", "StoreSolution");
    model.sol("sol4").feature().create("su76", "StoreSolution");
    model.sol("sol4").feature().create("su77", "StoreSolution");
    model.sol("sol4").feature().create("su78", "StoreSolution");
    model.sol("sol4").feature().create("su79", "StoreSolution");
    model.sol("sol4").feature().create("su80", "StoreSolution");
    model.sol("sol4").feature().create("su81", "StoreSolution");
    model.sol("sol4").feature().create("su82", "StoreSolution");
    model.sol("sol4").feature().create("su83", "StoreSolution");
    model.sol("sol4").feature().create("su84", "StoreSolution");
    model.sol("sol4").feature().create("su85", "StoreSolution");
    model.sol("sol4").feature().create("su86", "StoreSolution");
    model.sol("sol4").feature().create("su87", "StoreSolution");
    model.sol("sol4").feature().create("su88", "StoreSolution");
    model.sol("sol4").feature().create("su89", "StoreSolution");
    model.sol("sol4").feature().create("su90", "StoreSolution");
    model.sol("sol4").feature().create("su91", "StoreSolution");
    model.sol("sol4").feature().create("su92", "StoreSolution");
    model.sol("sol4").feature().create("su93", "StoreSolution");
    model.sol("sol4").feature().create("su94", "StoreSolution");
    model.sol("sol4").feature().create("su95", "StoreSolution");
    model.sol("sol4").feature().create("su96", "StoreSolution");
    model.sol("sol4").feature().create("su97", "StoreSolution");
    model.sol("sol4").feature().create("su98", "StoreSolution");
    model.sol("sol4").feature().create("su99", "StoreSolution");
    model.sol("sol4").feature().create("su100", "StoreSolution");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol5").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol6").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol7").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol8").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol9").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol10").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol11").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol12").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol13").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol14").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol15").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol16").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol17").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol18").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol19").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol20").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol21").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol22").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol23").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol24").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol25").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol26").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol27").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol28").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol29").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol30").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol31").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol32").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol33").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol34").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol35").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol36").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol37").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol38").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol39").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol40").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol41").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol42").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol43").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol44").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol45").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol46").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol47").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol48").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol49").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol50").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol51").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol52").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol53").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol54").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol55").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol56").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol57").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol58").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol59").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol60").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol61").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol62").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol63").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol64").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol65").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol66").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol67").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol68").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol69").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol70").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol71").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol72").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol73").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol74").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol75").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol76").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol77").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol78").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol79").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol80").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol81").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol82").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol83").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol84").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol85").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol86").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol87").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol88").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol89").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol90").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol91").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol92").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol93").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol94").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol95").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol96").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol97").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol98").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol99").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol100").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol101").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol102").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol103").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.sol("sol104").study("std3");

    model.study("std1").feature("freq").set("initstudyhide", "on");
    model.study("std1").feature("freq").set("initsolhide", "on");
    model.study("std1").feature("freq").set("notstudyhide", "on");
    model.study("std1").feature("freq").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std3").feature("time").set("initstudyhide", "on");
    model.study("std3").feature("time").set("initsolhide", "on");
    model.study("std3").feature("time").set("notstudyhide", "on");
    model.study("std3").feature("time").set("notsolhide", "on");

    model.batch().create("p1", "Parametric");
    model.batch("p1").feature().create("so1", "Solutionseq");
    model.batch("p1").study("std3");

    model.result().dataset().create("surf1", "Surface");
    model.result().dataset().create("av1", "Average");
    model.result().dataset().create("cpt1", "CutPoint3D");
    model.result().dataset().create("cpt2", "CutPoint3D");
    model.result().dataset().create("cpt3", "CutPoint3D");
    model.result().dataset().create("cpt4", "CutPoint3D");
    model.result().dataset().create("surf2", "Surface");
    model.result().dataset().create("an19_ds1", "Function1D");
    model.result().dataset().create("cpt5", "CutPoint3D");
    model.result().dataset().create("cpt6", "CutPoint3D");
    model.result().dataset().create("cpt7", "CutPoint3D");
    model.result().dataset().create("cpt8", "CutPoint3D");
    model.result().dataset("surf1").set("data", "dset2");
    model.result().dataset("surf1").selection().set(new int[]{3});
    model.result().dataset("av1").set("data", "surf1");
    model.result().dataset("cpt1").set("data", "dset2");
    model.result().dataset("surf2").selection().set(new int[]{3});
    model.result().dataset("cpt5").set("data", "dset4");
    model.result().dataset("cpt6").set("data", "dset4");
    model.result().dataset("cpt7").set("data", "dset4");
    model.result().dataset("cpt8").set("data", "dset4");
    model.result().dataset().remove("dset5");
    model.result().dataset().remove("dset6");
    model.result().dataset().remove("dset7");
    model.result().dataset().remove("dset8");
    model.result().dataset().remove("dset9");
    model.result().dataset().remove("dset10");
    model.result().dataset().remove("dset11");
    model.result().dataset().remove("dset12");
    model.result().dataset().remove("dset13");
    model.result().dataset().remove("dset14");
    model.result().dataset().remove("dset15");
    model.result().dataset().remove("dset16");
    model.result().dataset().remove("dset17");
    model.result().dataset().remove("dset18");
    model.result().dataset().remove("dset19");
    model.result().dataset().remove("dset20");
    model.result().dataset().remove("dset21");
    model.result().dataset().remove("dset22");
    model.result().dataset().remove("dset23");
    model.result().dataset().remove("dset24");
    model.result().dataset().remove("dset25");
    model.result().dataset().remove("dset26");
    model.result().dataset().remove("dset27");
    model.result().dataset().remove("dset28");
    model.result().dataset().remove("dset29");
    model.result().dataset().remove("dset30");
    model.result().dataset().remove("dset31");
    model.result().dataset().remove("dset32");
    model.result().dataset().remove("dset33");
    model.result().dataset().remove("dset34");
    model.result().dataset().remove("dset35");
    model.result().dataset().remove("dset36");
    model.result().dataset().remove("dset37");
    model.result().dataset().remove("dset38");
    model.result().dataset().remove("dset39");
    model.result().dataset().remove("dset40");
    model.result().dataset().remove("dset41");
    model.result().dataset().remove("dset42");
    model.result().dataset().remove("dset43");
    model.result().dataset().remove("dset44");
    model.result().dataset().remove("dset45");
    model.result().dataset().remove("dset46");
    model.result().dataset().remove("dset47");
    model.result().dataset().remove("dset48");
    model.result().dataset().remove("dset49");
    model.result().dataset().remove("dset50");
    model.result().dataset().remove("dset51");
    model.result().dataset().remove("dset52");
    model.result().dataset().remove("dset53");
    model.result().dataset().remove("dset54");
    model.result().dataset().remove("dset55");
    model.result().dataset().remove("dset56");
    model.result().dataset().remove("dset57");
    model.result().dataset().remove("dset58");
    model.result().dataset().remove("dset59");
    model.result().dataset().remove("dset60");
    model.result().dataset().remove("dset61");
    model.result().dataset().remove("dset62");
    model.result().dataset().remove("dset63");
    model.result().dataset().remove("dset64");
    model.result().dataset().remove("dset65");
    model.result().dataset().remove("dset66");
    model.result().dataset().remove("dset67");
    model.result().dataset().remove("dset68");
    model.result().dataset().remove("dset69");
    model.result().dataset().remove("dset70");
    model.result().dataset().remove("dset71");
    model.result().dataset().remove("dset72");
    model.result().dataset().remove("dset73");
    model.result().dataset().remove("dset74");
    model.result().dataset().remove("dset75");
    model.result().dataset().remove("dset76");
    model.result().dataset().remove("dset77");
    model.result().dataset().remove("dset78");
    model.result().dataset().remove("dset79");
    model.result().dataset().remove("dset80");
    model.result().dataset().remove("dset81");
    model.result().dataset().remove("dset82");
    model.result().dataset().remove("dset83");
    model.result().dataset().remove("dset84");
    model.result().dataset().remove("dset85");
    model.result().dataset().remove("dset86");
    model.result().dataset().remove("dset87");
    model.result().dataset().remove("dset88");
    model.result().dataset().remove("dset89");
    model.result().dataset().remove("dset90");
    model.result().dataset().remove("dset91");
    model.result().dataset().remove("dset92");
    model.result().dataset().remove("dset93");
    model.result().dataset().remove("dset94");
    model.result().dataset().remove("dset95");
    model.result().dataset().remove("dset96");
    model.result().dataset().remove("dset97");
    model.result().dataset().remove("dset98");
    model.result().dataset().remove("dset99");
    model.result().dataset().remove("dset100");
    model.result().dataset().remove("dset101");
    model.result().dataset().remove("dset102");
    model.result().dataset().remove("dset103");
    model.result().dataset().remove("dset104");
    model.result().create("pg1", "PlotGroup3D");
    model.result().create("pg2", "PlotGroup3D");
    model.result().create("pg3", "PlotGroup1D");
    model.result().create("pg4", "PlotGroup1D");
    model.result().create("pg5", "PlotGroup3D");
    model.result().create("pg6", "PlotGroup1D");
    model.result().create("pg7", "PlotGroup1D");
    model.result().create("pg8", "PlotGroup3D");
    model.result("pg1").feature().create("surf1", "Surface");
    model.result("pg1").feature("surf1").feature().create("def", "Deform");
    model.result("pg2").feature().create("surf1", "Surface");
    model.result("pg2").feature("surf1").feature().create("def", "Deform");
    model.result("pg3").feature().create("ptgr1", "PointGraph");
    model.result("pg4").feature().create("ptgr1", "PointGraph");
    model.result("pg4").feature().create("ptgr2", "PointGraph");
    model.result("pg4").feature().create("ptgr3", "PointGraph");
    model.result("pg5").feature().create("surf1", "Surface");
    model.result("pg5").feature("surf1").feature().create("def", "Deform");
    model.result("pg6").feature().create("ptgr1", "PointGraph");
    model.result("pg6").feature().create("ptgr2", "PointGraph");
    model.result("pg6").feature().create("ptgr3", "PointGraph");
    model.result("pg6").feature().create("ptgr4", "PointGraph");
    model.result("pg7").feature().create("plot1", "LineGraph");
    model.result("pg8").feature().create("surf1", "Surface");
    model.result("pg8").feature("surf1").feature().create("def", "Deform");
    model.result().export().create("plot1", "Plot");

    model.study("std1").name("Study 2.1");
    model.study("std1").feature("freq")
         .set("plist", "range(1000,1000,50000)");
    model.study("std2").name("Study 1");
    model.study("std2").feature("eig").set("neigs", "20");
    model.study("std2").feature("eig").set("shift", "5.0e3");
    model.study("std3").feature("param")
         .set("pname", new String[]{"sourceRho", "sourceAngle"});
    model.study("std3").feature("param")
         .set("plistarr", new String[]{"1.44421931426,1.54660218095,10.9421981849,14.4164093258,1.41745683668,1.92863959007,9.87117019923,14.6139539567,14.2538634862,22.3767891538,1.14832330239,16.0029945534,22.8340263945,7.65589962336,28.6979698416,20.4320768019,7.20255328884,0.687084654136,29.7907510756,14.7761258601,9.17188296429,15.8909196727,25.62303667,10.708292107,6.54026179395,16.9604409464,27.2511461861,26.7767714509,18.3845734125,28.0732532894,24.4225730892,1.99361109968,29.7243109339,16.1365844516,10.9827795339,14.6551022082,6.8272144606,25.8847224401,5.90701822294,28.6355063184,10.0314475777,0.736414290463,4.517485967,28.5773893508,29.2671883781,11.027470315,20.5664032609,3.76206205784,2.47510583127,1.6961303014,11.8886169156,21.6259277846,9.25017024754,11.0726079572,13.3810075144,20.8712789959,4.38566863872,3.57988987306,19.6244392266,7.94265985253,6.18384134971,2.07099999828,7.49365189633,18.7964037012,6.41086925478,2.28201057348,0.0668177298252,5.94768073328,7.0136072251,9.61389094926,9.14731184203,22.3645642509,2.84667745311,9.39702379801,14.1137333335,16.7637202192,4.19544452751,26.6922291052,17.7282951517,12.5361051782,1.07751525609,28.3359964898,13.86149816,4.67638944861,25.2401928397,26.1316398584,24.2954681347,2.17624185413,3.70013010869,23.3735111586,2.61441447995,28.7059087357,25.0656790337,12.8585581467,18.7558532908,13.1866257208,28.5312759111,27.4173068283,8.63928015669,6.15271173428", "2.24665610163,5.25097001309,4.02986342934,3.78893904889,4.50654694199,4.16163177275,1.2793791968,1.04150472146,1.47756312483,5.34252934718,0.418000761652,5.59728942221,5.9939045595,0.193226672846,1.91739425931,0.142210749751,2.03321049407,1.08231912455,4.28700434452,2.46435509325,5.43527410106,4.11564427151,5.8207044901,3.92397680225,4.19059736379,0.538525649766,2.73754207954,1.01003637628,2.93536777722,3.92207818487,2.37294039095,1.59266754477,3.7476062468,6.04901927211,2.96432075467,3.75092164681,1.28725362174,5.84570120465,0.406650971491,5.90429599065,3.52150385308,2.28972715919,0.0288341383296,2.06988781659,0.0771284247436,5.12749195802,5.59096753702,1.39041478553,6.26501155698,0.372075329991,0.218545677385,6.22517123355,2.58443467619,1.25388427194,3.1832321537,3.11698032915,4.90501397365,4.04730423978,4.80583464455,0.117993938552,2.83525994471,5.8567646275,3.39513861806,0.324704476272,0.711462287567,5.65086603512,5.13676960415,5.72950286586,3.33876824703,4.7085556728,2.78827299852,5.93951359647,6.0620731779,2.47613676339,1.87266930543,1.14053653198,4.72054450001,5.10494944639,4.70604515746,2.22020052814,2.69378410862,3.56521083472,5.47801962226,4.20733497653,4.5718997752,2.97310126012,1.89848745423,4.09273254396,2.75721478409,3.20001410894,5.96541964991,4.37875735161,2.31349676764,5.15672742561,0.359063152703,5.95019520577,2.29316571703,3.58380692729,1.30112693886,4.4167073275"});
    model.study("std3").feature("time").set("tunit", "\u00b5s");
    model.study("std3").feature("time").set("tlist", "range(0,2.5,300)");
    model.study("std3").feature("time")
         .set("activate", new String[]{"solid", "off", "solid2", "on"});

    model.sol("sol1").attach("std2");
    model.sol("sol1").feature("st1")
         .name("Compile Equations: Eigenfrequency");
    model.sol("sol1").feature("st1").set("study", "std2");
    model.sol("sol1").feature("v1").set("control", "eig");
    model.sol("sol1").feature("e1").set("control", "eig");
    model.sol("sol1").feature("e1").set("neigs", "20");
    model.sol("sol1").feature("e1").set("transform", "eigenfrequency");
    model.sol("sol1").feature("e1").set("rtol", "1.0E-3");
    model.sol("sol1").feature("e1").set("shift", "5.0e3");
    model.sol("sol2").attach("std1");
    model.sol("sol2").feature("st1")
         .name("Compile Equations: Frequency Domain");
    model.sol("sol2").feature("st1").set("studystep", "freq");
    model.sol("sol2").feature("st1").set("splitcomplex", true);
    model.sol("sol2").feature("v1").set("control", "freq");
    model.sol("sol2").feature("s1").set("control", "freq");
    model.sol("sol2").feature("s1").feature("aDef").set("complexfun", true);
    model.sol("sol2").feature("s1").feature("p1").set("preusesol", "auto");
    model.sol("sol2").feature("s1").feature("p1").set("control", "freq");
    model.sol("sol2").feature("s1").feature("p1")
         .set("pcontinuationmode", "no");
    model.sol("sol2").feature("s1").feature("p1")
         .set("pname", new String[]{"freq"});
    model.sol("sol2").feature("s1").feature("p1")
         .set("plistarr", new String[]{"range(1000,1000,50000)"});
    model.sol("sol3").attach("std3");
    model.sol("sol3").feature("st1")
         .name("Compile Equations: Time Dependent");
    model.sol("sol3").feature("st1").set("splitcomplex", true);
    model.sol("sol3").feature("st1").set("study", "std3");
    model.sol("sol3").feature("v1").set("control", "time");
    model.sol("sol3").feature("v1").feature("mod1_u2")
         .set("scalemethod", "manual");
    model.sol("sol3").feature("v1").feature("mod1_u2")
         .set("scaleval", "1e-2*0.44399472969845044");
    model.sol("sol3").feature("v1").feature("mod1_u").set("solvefor", false);
    model.sol("sol3").feature("t1")
         .set("atol", new String[]{"mod1_u2", "1e-3", "mod1_u", "1e-3"});
    model.sol("sol3").feature("t1").set("tlist", "range(0,2.5,300)");
    model.sol("sol3").feature("t1").set("fieldselection", "mod1_u2");
    model.sol("sol3").feature("t1").set("timemethod", "genalpha");
    model.sol("sol3").feature("t1")
         .set("atoludotactive", new String[]{"mod1_u2", "off", "mod1_u", "off"});
    model.sol("sol3").feature("t1")
         .set("atoludot", new String[]{"mod1_u2", "1e-3", "mod1_u", "1e-3"});
    model.sol("sol3").feature("t1")
         .set("atolmethod", new String[]{"mod1_u2", "global", "mod1_u", "global"});
    model.sol("sol3").feature("t1").set("timestepgenalpha", "2.5e-6");
    model.sol("sol3").feature("t1").set("tstepsgenalpha", "manual");
    model.sol("sol3").feature("t1").set("control", "time");
    model.sol("sol3").feature("t1").set("rhoinf", "0");
    model.sol("sol3").feature("t1").set("tunit", "\u00b5s");
    model.sol("sol4").name("Parametric 4");

    model.batch("p1").set("control", "param");
    model.batch("p1").set("err", true);
    model.batch("p1")
         .set("plistarr", new String[]{"1.44421931426,1.54660218095,10.9421981849,14.4164093258,1.41745683668,1.92863959007,9.87117019923,14.6139539567,14.2538634862,22.3767891538,1.14832330239,16.0029945534,22.8340263945,7.65589962336,28.6979698416,20.4320768019,7.20255328884,0.687084654136,29.7907510756,14.7761258601,9.17188296429,15.8909196727,25.62303667,10.708292107,6.54026179395,16.9604409464,27.2511461861,26.7767714509,18.3845734125,28.0732532894,24.4225730892,1.99361109968,29.7243109339,16.1365844516,10.9827795339,14.6551022082,6.8272144606,25.8847224401,5.90701822294,28.6355063184,10.0314475777,0.736414290463,4.517485967,28.5773893508,29.2671883781,11.027470315,20.5664032609,3.76206205784,2.47510583127,1.6961303014,11.8886169156,21.6259277846,9.25017024754,11.0726079572,13.3810075144,20.8712789959,4.38566863872,3.57988987306,19.6244392266,7.94265985253,6.18384134971,2.07099999828,7.49365189633,18.7964037012,6.41086925478,2.28201057348,0.0668177298252,5.94768073328,7.0136072251,9.61389094926,9.14731184203,22.3645642509,2.84667745311,9.39702379801,14.1137333335,16.7637202192,4.19544452751,26.6922291052,17.7282951517,12.5361051782,1.07751525609,28.3359964898,13.86149816,4.67638944861,25.2401928397,26.1316398584,24.2954681347,2.17624185413,3.70013010869,23.3735111586,2.61441447995,28.7059087357,25.0656790337,12.8585581467,18.7558532908,13.1866257208,28.5312759111,27.4173068283,8.63928015669,6.15271173428", "2.24665610163,5.25097001309,4.02986342934,3.78893904889,4.50654694199,4.16163177275,1.2793791968,1.04150472146,1.47756312483,5.34252934718,0.418000761652,5.59728942221,5.9939045595,0.193226672846,1.91739425931,0.142210749751,2.03321049407,1.08231912455,4.28700434452,2.46435509325,5.43527410106,4.11564427151,5.8207044901,3.92397680225,4.19059736379,0.538525649766,2.73754207954,1.01003637628,2.93536777722,3.92207818487,2.37294039095,1.59266754477,3.7476062468,6.04901927211,2.96432075467,3.75092164681,1.28725362174,5.84570120465,0.406650971491,5.90429599065,3.52150385308,2.28972715919,0.0288341383296,2.06988781659,0.0771284247436,5.12749195802,5.59096753702,1.39041478553,6.26501155698,0.372075329991,0.218545677385,6.22517123355,2.58443467619,1.25388427194,3.1832321537,3.11698032915,4.90501397365,4.04730423978,4.80583464455,0.117993938552,2.83525994471,5.8567646275,3.39513861806,0.324704476272,0.711462287567,5.65086603512,5.13676960415,5.72950286586,3.33876824703,4.7085556728,2.78827299852,5.93951359647,6.0620731779,2.47613676339,1.87266930543,1.14053653198,4.72054450001,5.10494944639,4.70604515746,2.22020052814,2.69378410862,3.56521083472,5.47801962226,4.20733497653,4.5718997752,2.97310126012,1.89848745423,4.09273254396,2.75721478409,3.20001410894,5.96541964991,4.37875735161,2.31349676764,5.15672742561,0.359063152703,5.95019520577,2.29316571703,3.58380692729,1.30112693886,4.4167073275"});
    model.batch("p1").set("pname", new String[]{"sourceRho", "sourceAngle"});
    model.batch("p1").set("control", "param");
    model.batch("p1").feature("so1").set("psol", "sol4");
    model.batch("p1").feature("so1")
         .set("param", new String[]{"\"sourceRho\",\"82.114004\",\"sourceAngle\",\"6.257828\"", "\"sourceRho\",\"27.967761\",\"sourceAngle\",\"3.855198\"", "\"sourceRho\",\"73.728073\",\"sourceAngle\",\"0.839188\"", "\"sourceRho\",\"30.30902\",\"sourceAngle\",\"4.384502\"", "\"sourceRho\",\"40.752099\",\"sourceAngle\",\"3.755421\"", "\"sourceRho\",\"43.289552\",\"sourceAngle\",\"1.765497\"", "\"sourceRho\",\"116.925287\",\"sourceAngle\",\"4.115228\"", "\"sourceRho\",\"6.57638\",\"sourceAngle\",\"3.152214\"", "\"sourceRho\",\"121.263351\",\"sourceAngle\",\"0.310513\"", "\"sourceRho\",\"119.149654\",\"sourceAngle\",\"5.163228\"", 
         "\"sourceRho\",\"130.189802\",\"sourceAngle\",\"2.637059\"", "\"sourceRho\",\"37.873242\",\"sourceAngle\",\"3.345438\"", "\"sourceRho\",\"50.044968\",\"sourceAngle\",\"1.967042\"", "\"sourceRho\",\"96.202337\",\"sourceAngle\",\"0.327635\"", "\"sourceRho\",\"45.319741\",\"sourceAngle\",\"0.423004\"", "\"sourceRho\",\"51.301297\",\"sourceAngle\",\"4.60366\"", "\"sourceRho\",\"46.044228\",\"sourceAngle\",\"4.410934\"", "\"sourceRho\",\"119.987827\",\"sourceAngle\",\"3.543599\"", "\"sourceRho\",\"59.237347\",\"sourceAngle\",\"5.097084\"", "\"sourceRho\",\"111.509063\",\"sourceAngle\",\"0.075979\"", 
         "\"sourceRho\",\"98.58074\",\"sourceAngle\",\"3.551201\"", "\"sourceRho\",\"32.445187\",\"sourceAngle\",\"4.527906\"", "\"sourceRho\",\"33.470288\",\"sourceAngle\",\"0.104787\"", "\"sourceRho\",\"8.828789\",\"sourceAngle\",\"4.393967\"", "\"sourceRho\",\"86.404885\",\"sourceAngle\",\"2.177754\"", "\"sourceRho\",\"8.818957\",\"sourceAngle\",\"2.348214\"", "\"sourceRho\",\"93.508674\",\"sourceAngle\",\"1.203206\"", "\"sourceRho\",\"96.230175\",\"sourceAngle\",\"0.531425\"", "\"sourceRho\",\"1.531113\",\"sourceAngle\",\"5.230675\"", "\"sourceRho\",\"128.205643\",\"sourceAngle\",\"5.013509\"", 
         "\"sourceRho\",\"33.261489\",\"sourceAngle\",\"3.269015\"", "\"sourceRho\",\"53.93926\",\"sourceAngle\",\"1.464045\"", "\"sourceRho\",\"99.89094\",\"sourceAngle\",\"4.342674\"", "\"sourceRho\",\"26.599782\",\"sourceAngle\",\"0.325144\"", "\"sourceRho\",\"51.991764\",\"sourceAngle\",\"0.896172\"", "\"sourceRho\",\"31.497371\",\"sourceAngle\",\"5.080443\"", "\"sourceRho\",\"120.687362\",\"sourceAngle\",\"0.091323\"", "\"sourceRho\",\"95.054175\",\"sourceAngle\",\"1.700273\"", "\"sourceRho\",\"35.6252\",\"sourceAngle\",\"5.882064\"", "\"sourceRho\",\"58.583614\",\"sourceAngle\",\"2.58727\"", 
         "\"sourceRho\",\"44.770252\",\"sourceAngle\",\"4.94552\"", "\"sourceRho\",\"39.210685\",\"sourceAngle\",\"5.584227\"", "\"sourceRho\",\"107.590352\",\"sourceAngle\",\"1.419475\"", "\"sourceRho\",\"19.173875\",\"sourceAngle\",\"0.832464\"", "\"sourceRho\",\"36.111938\",\"sourceAngle\",\"4.927592\"", "\"sourceRho\",\"118.788107\",\"sourceAngle\",\"5.32242\"", "\"sourceRho\",\"121.149003\",\"sourceAngle\",\"3.401205\"", "\"sourceRho\",\"34.402877\",\"sourceAngle\",\"1.24935\"", "\"sourceRho\",\"124.920296\",\"sourceAngle\",\"4.764794\"", "\"sourceRho\",\"82.435352\",\"sourceAngle\",\"5.459306\"", 
         "\"sourceRho\",\"8.792469\",\"sourceAngle\",\"2.182021\"", "\"sourceRho\",\"12.804106\",\"sourceAngle\",\"2.084995\"", "\"sourceRho\",\"74.686642\",\"sourceAngle\",\"4.917318\"", "\"sourceRho\",\"37.032682\",\"sourceAngle\",\"1.626287\"", "\"sourceRho\",\"19.301717\",\"sourceAngle\",\"4.758281\"", "\"sourceRho\",\"66.491559\",\"sourceAngle\",\"1.408692\"", "\"sourceRho\",\"122.919146\",\"sourceAngle\",\"2.911787\"", "\"sourceRho\",\"49.087632\",\"sourceAngle\",\"1.897518\"", "\"sourceRho\",\"96.967143\",\"sourceAngle\",\"2.099764\"", "\"sourceRho\",\"60.124275\",\"sourceAngle\",\"4.805341\"", 
         "\"sourceRho\",\"85.404432\",\"sourceAngle\",\"3.253393\"", "\"sourceRho\",\"43.78941\",\"sourceAngle\",\"4.622219\"", "\"sourceRho\",\"58.254901\",\"sourceAngle\",\"1.363399\"", "\"sourceRho\",\"78.524771\",\"sourceAngle\",\"4.152765\"", "\"sourceRho\",\"68.778721\",\"sourceAngle\",\"4.133799\"", "\"sourceRho\",\"132.674525\",\"sourceAngle\",\"0.892445\"", "\"sourceRho\",\"30.995824\",\"sourceAngle\",\"0.922192\"", "\"sourceRho\",\"136.246801\",\"sourceAngle\",\"1.63193\"", "\"sourceRho\",\"139.873137\",\"sourceAngle\",\"4.955488\"", "\"sourceRho\",\"70.458684\",\"sourceAngle\",\"4.291903\"", 
         "\"sourceRho\",\"129.513176\",\"sourceAngle\",\"4.112191\"", "\"sourceRho\",\"112.68322\",\"sourceAngle\",\"4.97473\"", "\"sourceRho\",\"131.535199\",\"sourceAngle\",\"3.772799\"", "\"sourceRho\",\"103.595888\",\"sourceAngle\",\"0.24631\"", "\"sourceRho\",\"122.22752\",\"sourceAngle\",\"1.830505\"", "\"sourceRho\",\"2.700846\",\"sourceAngle\",\"2.979076\"", "\"sourceRho\",\"96.219343\",\"sourceAngle\",\"2.052922\"", "\"sourceRho\",\"61.797908\",\"sourceAngle\",\"3.097886\"", "\"sourceRho\",\"127.4077\",\"sourceAngle\",\"1.450974\"", "\"sourceRho\",\"113.351677\",\"sourceAngle\",\"4.771867\"", 
         "\"sourceRho\",\"121.910384\",\"sourceAngle\",\"3.625269\"", "\"sourceRho\",\"33.707571\",\"sourceAngle\",\"4.581497\"", "\"sourceRho\",\"37.906606\",\"sourceAngle\",\"3.588758\"", "\"sourceRho\",\"105.947073\",\"sourceAngle\",\"0.770364\"", "\"sourceRho\",\"24.138393\",\"sourceAngle\",\"2.398523\"", "\"sourceRho\",\"74.248297\",\"sourceAngle\",\"3.555244\"", "\"sourceRho\",\"132.973661\",\"sourceAngle\",\"2.247444\"", "\"sourceRho\",\"14.184875\",\"sourceAngle\",\"4.310776\"", "\"sourceRho\",\"6.514995\",\"sourceAngle\",\"2.837498\"", "\"sourceRho\",\"88.43381\",\"sourceAngle\",\"1.434422\"", 
         "\"sourceRho\",\"21.810176\",\"sourceAngle\",\"1.121466\"", "\"sourceRho\",\"28.617356\",\"sourceAngle\",\"5.458019\"", "\"sourceRho\",\"134.178615\",\"sourceAngle\",\"4.826383\"", "\"sourceRho\",\"99.972598\",\"sourceAngle\",\"5.223616\"", "\"sourceRho\",\"97.629124\",\"sourceAngle\",\"6.189622\"", "\"sourceRho\",\"35.466264\",\"sourceAngle\",\"5.881244\"", "\"sourceRho\",\"54.459154\",\"sourceAngle\",\"2.040152\"", "\"sourceRho\",\"98.459708\",\"sourceAngle\",\"1.673313\"", "\"sourceRho\",\"45.000694\",\"sourceAngle\",\"2.045692\"", "\"sourceRho\",\"131.108049\",\"sourceAngle\",\"2.593972\""});
    model.batch("p1").feature("so1").set("seq", "sol3");
    model.batch("p1").attach("std3");
    model.batch("p1").run();

    model.result().dataset("dset1").set("phase", "185");
    model.result().dataset("surf1").name("Upstream Surface");
    model.result().dataset("av1").name("Upstream Surface Average");
    model.result().dataset("cpt1").name("S0");
    model.result().dataset("cpt1")
         .set("pointx", "0.919  * innerR * cos(theta_0)");
    model.result().dataset("cpt1")
         .set("pointy", "0.919  * innerR * sin(theta_0)");
    model.result().dataset("cpt1").set("pointz", "-(cylinderH/2+endH)");
    model.result().dataset("cpt1").set("bndsnap", true);
    model.result().dataset("cpt2").name("S0 Sol1");
    model.result().dataset("cpt2")
         .set("pointx", "0.919  * innerR * cos(theta_0)");
    model.result().dataset("cpt2")
         .set("pointy", "0.919  * innerR * sin(theta_0)");
    model.result().dataset("cpt2").set("pointz", "-(cylinderH/2+endH)");
    model.result().dataset("cpt2").set("bndsnap", true);
    model.result().dataset("cpt3").name("Center Sol1");
    model.result().dataset("cpt3").set("pointx", "0");
    model.result().dataset("cpt3").set("pointy", "0");
    model.result().dataset("cpt3").set("pointz", "-(cylinderH/2+endH)");
    model.result().dataset("cpt3").set("bndsnap", true);
    model.result().dataset("cpt4").name("Radius Sol1");
    model.result().dataset("cpt4").set("pointx", "outerR");
    model.result().dataset("cpt4").set("pointy", "0");
    model.result().dataset("cpt4").set("pointz", "0");
    model.result().dataset("cpt4").set("bndsnap", true);
    model.result().dataset("surf2").name("Upstream Surface 1");
    model.result().dataset("surf2").set("param", "expr");
    model.result().dataset("an19_ds1").set("par1", "t");
    model.result().dataset("an19_ds1").set("parmax1", "2.6E-5");
    model.result().dataset("an19_ds1").set("function", "all");
    model.result().dataset("an19_ds1").set("parmin1", "1.8E-5");
    model.result().dataset("cpt5").name("UB1");
    model.result().dataset("cpt5").set("pointx", "50");
    model.result().dataset("cpt5").set("pointy", "60");
    model.result().dataset("cpt5").set("pointz", "-cylinderH/2-endH");
    model.result().dataset("cpt5").set("bndsnap", true);
    model.result().dataset("cpt6").name("UB2");
    model.result().dataset("cpt6").set("pointx", "-50");
    model.result().dataset("cpt6").set("pointy", "60");
    model.result().dataset("cpt6").set("pointz", "-cylinderH/2-endH");
    model.result().dataset("cpt6").set("bndsnap", true);
    model.result().dataset("cpt7").name("UB3");
    model.result().dataset("cpt7").set("pointx", "-50");
    model.result().dataset("cpt7").set("pointy", "-60");
    model.result().dataset("cpt7").set("pointz", "-cylinderH/2-endH");
    model.result().dataset("cpt7").set("bndsnap", true);
    model.result().dataset("cpt8").name("UB4");
    model.result().dataset("cpt8").set("pointx", "50");
    model.result().dataset("cpt8").set("pointy", "-60");
    model.result().dataset("cpt8").set("pointz", "-cylinderH/2-endH");
    model.result().dataset("cpt8").set("bndsnap", true);
    model.result("pg1").name("Mode Shape (solid)");
    model.result("pg1").feature("surf1").set("data", "dset1");
    model.result("pg1").feature("surf1").set("expr", "sqrt(u^2+v^2)");
    model.result("pg1").feature("surf1")
         .set("const", new String[][]{{"solid.refpntx", "0", "Reference point for moment computation, x coordinate"}, {"solid.refpnty", "0", "Reference point for moment computation, y coordinate"}, {"solid.refpntz", "0", "Reference point for moment computation, z coordinate"}});
    model.result("pg1").feature("surf1").set("descr", "sqrt(u^2+v^2)");
    model.result("pg1").feature("surf1").feature("def")
         .set("scale", "0.009314030486150343");
    model.result("pg1").feature("surf1").feature("def")
         .set("scaleactive", false);
    model.result("pg2").name("Stress (solid)");
    model.result("pg2").set("data", "dset2");
    model.result("pg2").feature("surf1").set("expr", "solid.mises");
    model.result("pg2").feature("surf1").set("unit", "N/m^2");
    model.result("pg2").feature("surf1")
         .set("const", new String[][]{{"solid.refpntx", "0", "Reference point for moment computation, x coordinate"}, {"solid.refpnty", "0", "Reference point for moment computation, y coordinate"}, {"solid.refpntz", "0", "Reference point for moment computation, z coordinate"}});
    model.result("pg2").feature("surf1").set("descr", "von Mises stress");
    model.result("pg2").feature("surf1").feature("def")
         .set("scale", "2.3709042078431107E7");
    model.result("pg2").feature("surf1").feature("def")
         .set("scaleactive", false);
    model.result("pg3").set("ylabel", "imag(w) (mm)");
    model.result("pg3").set("xlabel", "freq");
    model.result("pg3").set("xlabelactive", false);
    model.result("pg3").set("ylabelactive", false);
    model.result("pg3").feature("ptgr1").set("data", "cpt1");
    model.result("pg3").feature("ptgr1").set("expr", "imag(w)");
    model.result("pg3").feature("ptgr1")
         .set("const", new String[][]{{"solid.refpntx", "0", "Reference point for moment computation, x coordinate"}, {"solid.refpnty", "0", "Reference point for moment computation, y coordinate"}, {"solid.refpntz", "0", "Reference point for moment computation, z coordinate"}});
    model.result("pg3").feature("ptgr1").set("descr", "imag(w)");
    model.result("pg4").set("xlabel", "Solution number");
    model.result("pg4").set("xlabelactive", false);
    model.result("pg4").feature("ptgr1").name("S0");
    model.result("pg4").feature("ptgr1").set("data", "cpt2");
    model.result("pg4").feature("ptgr1").set("expr", "w");
    model.result("pg4").feature("ptgr1")
         .set("const", new String[][]{{"solid.refpntx", "0", "Reference point for moment computation, x coordinate"}, {"solid.refpnty", "0", "Reference point for moment computation, y coordinate"}, {"solid.refpntz", "0", "Reference point for moment computation, z coordinate"}});
    model.result("pg4").feature("ptgr1")
         .set("descr", "Displacement field, Z component");
    model.result("pg4").feature("ptgr2").name("Center");
    model.result("pg4").feature("ptgr2").set("data", "cpt3");
    model.result("pg4").feature("ptgr2").set("expr", "w");
    model.result("pg4").feature("ptgr2")
         .set("const", new String[][]{{"solid.refpntx", "0", "Reference point for moment computation, x coordinate"}, {"solid.refpnty", "0", "Reference point for moment computation, y coordinate"}, {"solid.refpntz", "0", "Reference point for moment computation, z coordinate"}});
    model.result("pg4").feature("ptgr2")
         .set("descr", "Displacement field, Z component");
    model.result("pg4").feature("ptgr3").name("Radius");
    model.result("pg4").feature("ptgr3").set("data", "cpt4");
    model.result("pg4").feature("ptgr3").set("expr", "u");
    model.result("pg4").feature("ptgr3")
         .set("const", new String[][]{{"solid.refpntx", "0", "Reference point for moment computation, x coordinate"}, {"solid.refpnty", "0", "Reference point for moment computation, y coordinate"}, {"solid.refpntz", "0", "Reference point for moment computation, z coordinate"}});
    model.result("pg4").feature("ptgr3")
         .set("descr", "Displacement field, X component");
    model.result("pg5").name("Stress (solid2)");
    model.result("pg5").set("data", "dset3");
    model.result("pg5").feature("surf1").set("expr", "solid2.mises");
    model.result("pg5").feature("surf1").set("unit", "N/m^2");
    model.result("pg5").feature("surf1").set("descr", "von Mises stress");
    model.result("pg6").name("1D Time-Domain Plots");
    model.result("pg6").set("data", "dset4");
    model.result("pg6")
         .set("ylabel", "Acceleration, Z component (m/s<sup>2</sup>)");
    model.result("pg6").set("xlabel", "Time (\u00b5s)");
    model.result("pg6").set("xlabelactive", false);
    model.result("pg6").set("ylabelactive", false);
    model.result("pg6").feature("ptgr1").name("UB1");
    model.result("pg6").feature("ptgr1").set("data", "cpt5");
    model.result("pg6").feature("ptgr1").set("expr", "solid2.u_ttZ");
    model.result("pg6").feature("ptgr1").set("unit", "m/s^2");
    model.result("pg6").feature("ptgr1")
         .set("descr", "Acceleration, Z component");
    model.result("pg6").feature("ptgr2").name("UB2");
    model.result("pg6").feature("ptgr2").set("data", "cpt6");
    model.result("pg6").feature("ptgr2").set("expr", "solid2.u_ttZ");
    model.result("pg6").feature("ptgr2").set("unit", "m/s^2");
    model.result("pg6").feature("ptgr2")
         .set("descr", "Acceleration, Z component");
    model.result("pg6").feature("ptgr3").name("UB3");
    model.result("pg6").feature("ptgr3").set("data", "cpt7");
    model.result("pg6").feature("ptgr3").set("expr", "solid2.u_ttZ");
    model.result("pg6").feature("ptgr3").set("unit", "m/s^2");
    model.result("pg6").feature("ptgr3")
         .set("descr", "Acceleration, Z component");
    model.result("pg6").feature("ptgr4").name("UB4");
    model.result("pg6").feature("ptgr4").set("data", "cpt8");
    model.result("pg6").feature("ptgr4").set("expr", "solid2.u_ttZ");
    model.result("pg6").feature("ptgr4").set("unit", "m/s^2");
    model.result("pg6").feature("ptgr4")
         .set("descr", "Acceleration, Z component");
    model.result("pg7").set("data", "none");
    model.result("pg7").set("ylabel", "shock(t) (Pa)");
    model.result("pg7").set("xlabel", "t (s)");
    model.result("pg7").set("xlabelactive", true);
    model.result("pg7").set("title", "shock(t) (Pa)");
    model.result("pg7").set("titletype", "manual");
    model.result("pg7").set("ylabelactive", true);
    model.result("pg7").feature("plot1").set("data", "an19_ds1");
    model.result("pg7").feature("plot1").set("solrepresentation", "solnum");
    model.result("pg7").feature("plot1").set("unit", "");
    model.result("pg7").feature("plot1").set("xdataunit", "");
    model.result("pg7").feature("plot1").set("xdata", "expr");
    model.result("pg7").feature("plot1").set("expr", "mod1.shock(root.t[s])");
    model.result("pg7").feature("plot1").set("xdataexpr", "root.t");
    model.result("pg7").feature("plot1").set("descr", "shock(t)");
    model.result("pg7").feature("plot1")
         .set("const", new String[][]{{"mod1.solid.refpntx", "0", "Reference point for moment computation, x coordinate"}, {"mod1.solid.refpnty", "0", "Reference point for moment computation, y coordinate"}, {"mod1.solid.refpntz", "0", "Reference point for moment computation, z coordinate"}, {"mod1.solid2.refpntx", "0", "Reference point for moment computation, x coordinate"}, {"mod1.solid2.refpnty", "0", "Reference point for moment computation, y coordinate"}, {"mod1.solid2.refpntz", "0", "Reference point for moment computation, z coordinate"}});
    model.result("pg7").feature("plot1").set("xdatadescr", "root.t");
    model.result("pg7").feature("plot1").set("xdataunit", "");
    model.result("pg8").name("Stress (solid2) 1");
    model.result("pg8").set("data", "dset4");
    model.result("pg8").feature("surf1").set("expr", "solid2.mises");
    model.result("pg8").feature("surf1").set("unit", "N/m^2");
    model.result("pg8").feature("surf1").set("descr", "von Mises stress");
    model.result("pg8").feature("surf1").feature("def")
         .set("expr", new String[]{"u2", "v2", "w2"});
    model.result().export("plot1")
         .set("filename", "C:\\Users\\plane\\Dropbox\\Research\\MTA\\Analysis\\HPRF\\Vibration Modes\\z_disp_vs_freq_100Hz_100Hz_25kHz.csv");
    model.result().export("plot1").set("plotgroup", "pg3");
    model.result().export("plot1").set("plot", "ptgr1");
    model.result().export("plot1").set("sort", true);

    model.sol("sol1").clearSolution();
    model.sol("sol2").clearSolution();
    model.sol("sol3").clearSolution();
    model.sol("sol4").clearSolution();
    model.sol("sol5").clearSolution();
    model.sol("sol6").clearSolution();
    model.sol("sol7").clearSolution();
    model.sol("sol8").clearSolution();
    model.sol("sol9").clearSolution();
    model.sol("sol10").clearSolution();
    model.sol("sol11").clearSolution();
    model.sol("sol12").clearSolution();
    model.sol("sol13").clearSolution();
    model.sol("sol14").clearSolution();
    model.sol("sol15").clearSolution();
    model.sol("sol16").clearSolution();
    model.sol("sol17").clearSolution();
    model.sol("sol18").clearSolution();
    model.sol("sol19").clearSolution();
    model.sol("sol20").clearSolution();
    model.sol("sol21").clearSolution();
    model.sol("sol22").clearSolution();
    model.sol("sol23").clearSolution();
    model.sol("sol24").clearSolution();
    model.sol("sol25").clearSolution();
    model.sol("sol26").clearSolution();
    model.sol("sol27").clearSolution();
    model.sol("sol28").clearSolution();
    model.sol("sol29").clearSolution();
    model.sol("sol30").clearSolution();
    model.sol("sol31").clearSolution();
    model.sol("sol32").clearSolution();
    model.sol("sol33").clearSolution();
    model.sol("sol34").clearSolution();
    model.sol("sol35").clearSolution();
    model.sol("sol36").clearSolution();
    model.sol("sol37").clearSolution();
    model.sol("sol38").clearSolution();
    model.sol("sol39").clearSolution();
    model.sol("sol40").clearSolution();
    model.sol("sol41").clearSolution();
    model.sol("sol42").clearSolution();
    model.sol("sol43").clearSolution();
    model.sol("sol44").clearSolution();
    model.sol("sol45").clearSolution();
    model.sol("sol46").clearSolution();
    model.sol("sol47").clearSolution();
    model.sol("sol48").clearSolution();
    model.sol("sol49").clearSolution();
    model.sol("sol50").clearSolution();
    model.sol("sol51").clearSolution();
    model.sol("sol52").clearSolution();
    model.sol("sol53").clearSolution();
    model.sol("sol54").clearSolution();
    model.sol("sol55").clearSolution();
    model.sol("sol56").clearSolution();
    model.sol("sol57").clearSolution();
    model.sol("sol58").clearSolution();
    model.sol("sol59").clearSolution();
    model.sol("sol60").clearSolution();
    model.sol("sol61").clearSolution();
    model.sol("sol62").clearSolution();
    model.sol("sol63").clearSolution();
    model.sol("sol64").clearSolution();
    model.sol("sol65").clearSolution();
    model.sol("sol66").clearSolution();
    model.sol("sol67").clearSolution();
    model.sol("sol68").clearSolution();
    model.sol("sol69").clearSolution();
    model.sol("sol70").clearSolution();
    model.sol("sol71").clearSolution();
    model.sol("sol72").clearSolution();
    model.sol("sol73").clearSolution();
    model.sol("sol74").clearSolution();
    model.sol("sol75").clearSolution();
    model.sol("sol76").clearSolution();
    model.sol("sol77").clearSolution();
    model.sol("sol78").clearSolution();
    model.sol("sol79").clearSolution();
    model.sol("sol80").clearSolution();
    model.sol("sol81").clearSolution();
    model.sol("sol82").clearSolution();
    model.sol("sol83").clearSolution();
    model.sol("sol84").clearSolution();
    model.sol("sol85").clearSolution();
    model.sol("sol86").clearSolution();
    model.sol("sol87").clearSolution();
    model.sol("sol88").clearSolution();
    model.sol("sol89").clearSolution();
    model.sol("sol90").clearSolution();
    model.sol("sol91").clearSolution();
    model.sol("sol92").clearSolution();
    model.sol("sol93").clearSolution();
    model.sol("sol94").clearSolution();
    model.sol("sol95").clearSolution();
    model.sol("sol96").clearSolution();
    model.sol("sol97").clearSolution();
    model.sol("sol98").clearSolution();
    model.sol("sol99").clearSolution();
    model.sol("sol100").clearSolution();
    model.sol("sol101").clearSolution();
    model.sol("sol102").clearSolution();
    model.sol("sol103").clearSolution();
    model.sol("sol104").clearSolution();

    return model;
  }

}

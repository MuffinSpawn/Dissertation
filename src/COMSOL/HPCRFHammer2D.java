/*
 * HPCRFHammer2D.java
 */

import com.comsol.model.*;
import com.comsol.model.util.*;

/** Model exported on Jan 17 2016, 15:30 by COMSOL 4.4.0.195. */
public class HPCRFHammer2D {

  public static void main(String[] args) {
    run();
  }

  public static Model run() {
    Model model = ModelUtil.create("Model");

    model.modelPath("C:\\Users\\plane\\Desktop\\COMSOL");

    model.name("HPRF RF Hammer 2D.mph");

    model.param().set("InnerRadius", "11.43 [cm]");
    model.param().set("OuterRadius", "15.24 [cm]");
    model.param().set("InnerHeight", "8.128 [cm]");
    model.param().set("EndHeight", "5.08 [cm]");
    model.param().set("ButtonRadius", "2.54 [cm]");
    model.param().set("ShimHeight", "0.635 [cm]");
    model.param().set("ShimGapRadius", "2.2225 [cm]");
    model.param().set("ShimGapHeight", "0.15748 [cm]");
    model.param().set("c_p_SS", "5790 [m/s]", "5790 [m/s]");
    model.param().set("c_s_SS", "3100 [m/s]", "3100 [m/s]");
    model.param().set("c_p_Cu", "4760 [m/s]", "4760 [m/s]");
    model.param().set("c_s_Cu", "2325 [m/s]", "2325 [m/s]");
    model.param()
         .set("c_N2", "540 [m/s]", "Speed of sound in N2 gas at density");
    model.param().set("rho_N2", "37.5 [kg/m^3]", "Density of N2 gas");
    model.param().set("z_S1", "0 [cm]");
    model.param().set("r_S0", "0.919*InnerRadius");
    model.param().set("t_p", "30 [us]");
    model.param().set("bolt_disp", "0 [cm]");
    model.param().set("notch_height", ".6731");
    model.param().set("r_S5", "0.919*InnerRadius");
    model.param().set("K_SS", "140 [GPa]");
    model.param().set("G_SS", "78[GPa]");
    model.param().set("rho_SS", "7970 [kg/m^3]");
    model.param().set("K_Cu", "120[GPa]");
    model.param().set("G_Cu", "46 [GPa]");
    model.param().set("rho_Cu", "8940 [kg/m^3]");
    model.param().set("RFrac", "0.7");
    model.param().set("k_tot_button_r_up", "1.0e15 [N/m]");
    model.param().set("k_tot_button_z_up", "2.2e9 [N/m]");
    model.param().set("k_tot_button_r_down", "1.0e15 [N/m]");
    model.param().set("k_tot_button_z_down", "2.2e9 [N/m]");
    model.param().set("k_tot_flange_r", "5e9 [N/m]");
    model.param().set("k_tot_flange_z", "1e12 [N/m]");
    model.param().set("t0", "2 [us]", "spark duration");
    model.param().set("gamma", "3", "spark decay rate");
    model.param().set("lambda", "1", "osc. cycles per t0");

    model.modelNode().create("comp1");

    model.func().create("gp1", "GaussianPulse");
    model.func().create("pw1", "Piecewise");
    model.func().create("an1", "Analytic");
    model.func().create("an8", "Analytic");
    model.func().create("an9", "Analytic");
    model.func().create("an10", "Analytic");
    model.func("gp1").set("sigma", "5e-7");
    model.func("gp1").set("location", "1e-6");
    model.func("pw1")
         .set("pieces", new String[][]{{"0", "t_p/3", "log(1+3*(exp(1)-1)/t_p*t)"}, {"t_p/3", "3*t_p/4", "1"}, {"3*t_p/4", "t_p", "-log(1-4*(1-exp(-1)) + 4*(1-exp(-1))/t_p*t)"}});
    model.func("pw1").set("argunit", "s");
    model.func("pw1").set("funcname", "RFEnvelope");
    model.func("pw1").set("arg", "t");
    model.func("an1")
         .set("args", new String[]{"x", "a0", "a1", "a2", "a3", "a4", "a5", "a6", "a7", "a8", 
         "a9", "a10", "a11", "a12"});
    model.func("an1")
         .set("expr", "a0+a1*x+a2*x^2+a3*x^3+a4*x^4+a5*x^5+a6*x^6+a7*x^7+a8*x^8+a9*x^9+a10*x^10+a11*x^11+a12*x^12");
    model.func("an1")
         .set("plotargs", new String[][]{{"x", "0", "1"}, {"a0", "0", "1"}, {"a1", "0", "1"}, {"a2", "0", "1"}, {"a3", "0", "1"}, {"a4", "0", "1"}, {"a5", "0", "1"}, {"a6", "0", "1"}, {"a7", "0", "1"}, {"a8", "0", "1"}, 
         {"a9", "0", "1"}, {"a10", "0", "1"}, {"a11", "0", "1"}, {"a12", "0", "1"}});
    model.func("an1").set("funcname", "polynomial");
    model.func("an8").set("args", new String[]{"rho"});
    model.func("an8").set("argunit", "cm");
    model.func("an8")
         .set("expr", "polynomial(rho,-240.178811905,-278.60505448,-178.332730546,72.9329677377,174.862318937,-146.980411248,53.7093343214,-11.4886388004,1.56357151204,-0.137733463426,0.00763183423149,-0.000242374455041,3.36971433768e-06)");
    model.func("an8")
         .set("plotargs", new String[][]{{"rho", "ButtonRadius", "InnerRadius"}});
    model.func("an8").set("funcname", "f_wall");
    model.func("an8").set("fununit", "N/m^2");
    model.func("an9").set("args", new String[]{"z"});
    model.func("an9").set("argunit", "cm");
    model.func("an9")
         .set("expr", "polynomial(z,-84.0445115747,9.21244614012e-05,-0.0257325876635,-0.00010904929815,0.00109329570891,4.34025393603e-05,2.64423467733e-05,-6.6999234955e-06,-6.44621761842e-06,4.29603467484e-07,3.99598700887e-07,-9.44057804598e-09,-8.75366850967e-09)");
    model.func("an9")
         .set("plotargs", new String[][]{{"z", "-InnerHeight/2", "InnerHeight/2"}});
    model.func("an9").set("funcname", "f_circ");
    model.func("an9").set("fununit", "N/m^2");
    model.func("an10").set("args", new String[]{"z"});
    model.func("an10").set("argunit", "cm");
    model.func("an10")
         .set("expr", "polynomial(z,15758.5245901,-19.6518218233,-9618.87504383,15.8366516429,2607.34389555,-4.31630431287,-372.886909702,0.521172056158,28.8134963162,-0.028761044486,-1.13644437236,0.000592426367832,0.0179375462308)");
    model.func("an10")
         .set("plotargs", new String[][]{{"z", "-InnerHeight/2", "InnerHeight/2"}});
    model.func("an10").set("funcname", "f_button");
    model.func("an10").set("fununit", "N/m^2");

    model.geom().create("geom1", 2);
    model.geom("geom1").axisymmetric(true);
    model.geom("geom1").feature().create("r12", "Rectangle");
    model.geom("geom1").feature().create("r18", "Rectangle");
    model.geom("geom1").feature().create("r19", "Rectangle");
    model.geom("geom1").feature().create("r20", "Rectangle");
    model.geom("geom1").feature().create("r4", "Rectangle");
    model.geom("geom1").feature().create("r1", "Rectangle");
    model.geom("geom1").feature().create("r2", "Rectangle");
    model.geom("geom1").feature().create("r5", "Rectangle");
    model.geom("geom1").feature().create("dif1", "Difference");
    model.geom("geom1").feature().create("r3", "Rectangle");
    model.geom("geom1").feature().create("r6", "Rectangle");
    model.geom("geom1").feature().create("dif2", "Difference");
    model.geom("geom1").feature().create("pol1", "Polygon");
    model.geom("geom1").feature().create("c1", "Circle");
    model.geom("geom1").feature().create("c2", "Circle");
    model.geom("geom1").feature().create("r7", "Rectangle");
    model.geom("geom1").feature().create("r10", "Rectangle");
    model.geom("geom1").feature().create("pol2", "Polygon");
    model.geom("geom1").feature().create("r8", "Rectangle");
    model.geom("geom1").feature().create("r9", "Rectangle");
    model.geom("geom1").feature().create("par1", "Partition");
    model.geom("geom1").feature().create("r13", "Rectangle");
    model.geom("geom1").feature().create("r16", "Rectangle");
    model.geom("geom1").feature().create("dif3", "Difference");
    model.geom("geom1").feature().create("r14", "Rectangle");
    model.geom("geom1").feature().create("r17", "Rectangle");
    model.geom("geom1").feature().create("r15", "Rectangle");
    model.geom("geom1").feature().create("b1", "BezierPolygon");
    model.geom("geom1").feature().create("pt1", "Point");
    model.geom("geom1").feature().create("pt2", "Point");
    model.geom("geom1").feature().create("pt3", "Point");
    model.geom("geom1").feature().create("sq1", "Square");
    model.geom("geom1").feature("r12").name("Environment");
    model.geom("geom1").feature("r12")
         .set("size", new String[]{"OuterRadius+EndHeight", "InnerHeight+4*EndHeight"});
    model.geom("geom1").feature("r12")
         .set("pos", new String[]{"0", "-InnerHeight/2-2*EndHeight"});
    model.geom("geom1").feature("r18").name("Downstream Flange");
    model.geom("geom1").feature("r18")
         .set("size", new String[]{"OuterRadius", "EndHeight"});
    model.geom("geom1").feature("r18")
         .set("pos", new String[]{"0", "InnerHeight/2"});
    model.geom("geom1").feature("r19").name("Center Flange");
    model.geom("geom1").feature("r19")
         .set("size", new String[]{"OuterRadius - InnerRadius", "InnerHeight"});
    model.geom("geom1").feature("r19")
         .set("pos", new String[]{"InnerRadius", "-InnerHeight/2"});
    model.geom("geom1").feature("r20").name("Upstream Flange");
    model.geom("geom1").feature("r20")
         .set("size", new String[]{"OuterRadius", "EndHeight"});
    model.geom("geom1").feature("r20")
         .set("pos", new String[]{"0", "-InnerHeight/2-EndHeight"});
    model.geom("geom1").feature("r4").active(false);
    model.geom("geom1").feature("r4").name("Cavity");
    model.geom("geom1").feature("r4")
         .set("size", new String[]{"15.24[cm]", "InnerHeight+2*EndHeight"});
    model.geom("geom1").feature("r4")
         .set("pos", new String[]{"0", "-InnerHeight/2-EndHeight"});
    model.geom("geom1").feature("r1").active(false);
    model.geom("geom1").feature("r1").name("Resonance Chamber");
    model.geom("geom1").feature("r1")
         .set("size", new String[]{"InnerRadius", "InnerHeight"});
    model.geom("geom1").feature("r1")
         .set("pos", new String[]{"0", "-InnerHeight/2"});
    model.geom("geom1").feature("r2").name("Upstream Button Shim");
    model.geom("geom1").feature("r2")
         .set("size", new String[]{"ButtonRadius", "ShimHeight"});
    model.geom("geom1").feature("r2")
         .set("pos", new String[]{"0", "-InnerHeight/2"});
    model.geom("geom1").feature("r5").name("Upstream Button Shim Gap");
    model.geom("geom1").feature("r5")
         .set("size", new String[]{"ShimGapRadius", "ShimGapHeight"});
    model.geom("geom1").feature("r5")
         .set("pos", new String[]{"0", "-InnerHeight/2"});
    model.geom("geom1").feature("dif1").selection("input")
         .set(new String[]{"r2"});
    model.geom("geom1").feature("dif1").selection("input2")
         .set(new String[]{"r5"});
    model.geom("geom1").feature("r3").name("Downstream Button Shim");
    model.geom("geom1").feature("r3")
         .set("size", new String[]{"ButtonRadius", "ShimHeight"});
    model.geom("geom1").feature("r3")
         .set("pos", new String[]{"0", "InnerHeight/2-ShimHeight"});
    model.geom("geom1").feature("r6").name("Downstream Button Shim Gap");
    model.geom("geom1").feature("r6")
         .set("size", new String[]{"ShimGapRadius", "ShimGapHeight"});
    model.geom("geom1").feature("r6")
         .set("pos", new String[]{"0", "InnerHeight/2-ShimGapHeight"});
    model.geom("geom1").feature("dif2").selection("input")
         .set(new String[]{"r3"});
    model.geom("geom1").feature("dif2").selection("input2")
         .set(new String[]{"r6"});
    model.geom("geom1").feature("pol1")
         .set("table", new String[][]{{"ShimGapRadius+.15875[cm]", "-InnerHeight/2"}, {"ShimGapRadius", "-InnerHeight/2+ShimGapHeight"}});
    model.geom("geom1").feature("pol1").set("type", "open");
    model.geom("geom1").feature("pol1").set("source", "table");
    model.geom("geom1").feature("c1").name("Upstream Button");
    model.geom("geom1").feature("c1").set("angle", "90");
    model.geom("geom1").feature("c1").set("r", "ButtonRadius");
    model.geom("geom1").feature("c1")
         .set("pos", new String[]{"0", "-InnerHeight/2+ShimHeight"});
    model.geom("geom1").feature("c2").name("Downstream Button");
    model.geom("geom1").feature("c2").set("angle", "90");
    model.geom("geom1").feature("c2").set("r", "ButtonRadius");
    model.geom("geom1").feature("c2").set("rot", "270");
    model.geom("geom1").feature("c2")
         .set("pos", new String[]{"0", "InnerHeight/2-ShimHeight"});
    model.geom("geom1").feature("r7").name("Beam Hole");
    model.geom("geom1").feature("r7")
         .set("size", new String[]{"0.00508", "3.4925 [cm]"});
    model.geom("geom1").feature("r7")
         .set("pos", new String[]{"0", "-InnerHeight/2-EndHeight"});
    model.geom("geom1").feature("r10").name("Upstream Button Bolt Bore");
    model.geom("geom1").feature("r10")
         .set("size", new String[]{"0.00508", "4[cm]"});
    model.geom("geom1").feature("r10")
         .set("pos", new String[]{"0", "-InnerHeight/2-1.27[cm]"});
    model.geom("geom1").feature("pol2")
         .set("table", new String[][]{{"ShimGapRadius+.15875[cm]", "InnerHeight/2"}, {"ShimGapRadius", "InnerHeight/2-ShimGapHeight"}});
    model.geom("geom1").feature("pol2").set("type", "open");
    model.geom("geom1").feature("pol2").set("source", "table");
    model.geom("geom1").feature("r8").active(false);
    model.geom("geom1").feature("r8").name("Gas Inlet");
    model.geom("geom1").feature("r8")
         .set("size", new String[]{"0.009075", "EndHeight"});
    model.geom("geom1").feature("r8")
         .set("pos", new String[]{"0.0851", "-CavityHeight/2-EndHeight"});
    model.geom("geom1").feature("r9").active(false);
    model.geom("geom1").feature("r9")
         .set("size", new String[]{"InnerRadius / 2.0", "InnerHeight + 2*EndHeight"});
    model.geom("geom1").feature("r9")
         .set("pos", new String[]{"0", "-InnerHeight/2 - EndHeight"});
    model.geom("geom1").feature("par1").active(false);
    model.geom("geom1").feature("par1").selection("input")
         .set(new String[]{});
    model.geom("geom1").feature("par1").selection("tool").set(new String[]{});
    model.geom("geom1").feature("r13").active(false);
    model.geom("geom1").feature("r13").name("Test Notch 1");
    model.geom("geom1").feature("r13")
         .set("size", new String[]{"2 [cm]", "1 [cm]"});
    model.geom("geom1").feature("r13")
         .set("pos", new String[]{"4 [cm]", "-InnerHeight/2-EndHeight"});
    model.geom("geom1").feature("r16").active(false);
    model.geom("geom1").feature("r16").name("Test Notch 2");
    model.geom("geom1").feature("r16")
         .set("size", new String[]{".635 [cm]", "notch_height"});
    model.geom("geom1").feature("r16")
         .set("pos", new String[]{"2.2225 [cm]", "-InnerHeight/2+.635 [cm]"});
    model.geom("geom1").feature("r16").set("base", "center");
    model.geom("geom1").feature("dif3").active(false);
    model.geom("geom1").feature("dif3").selection("input")
         .set(new String[]{"c1", "dif1"});
    model.geom("geom1").feature("dif3").selection("input2")
         .set(new String[]{});
    model.geom("geom1").feature("r14").name("Upstream Button Bolt");
    model.geom("geom1").feature("r14")
         .set("size", new String[]{".4445 [cm]", "2.54 [cm]"});
    model.geom("geom1").feature("r14")
         .set("pos", new String[]{".508 [cm]", "-InnerHeight/2-1.27[cm]"});
    model.geom("geom1").feature("r17")
         .name("Upstream Button Bolt Drill Space");
    model.geom("geom1").feature("r17")
         .set("size", new String[]{".23335 [cm]", ".23335 [cm]"});
    model.geom("geom1").feature("r17")
         .set("pos", new String[]{".508 [cm]", "-InnerHeight/2+1.27 [cm]"});
    model.geom("geom1").feature("r15").name("Downstream Button Bolt");
    model.geom("geom1").feature("r15")
         .set("size", new String[]{".9525 [cm]", "2.54 [cm]"});
    model.geom("geom1").feature("r15")
         .set("pos", new String[]{"0", "InnerHeight/2-1.27[cm]"});
    model.geom("geom1").feature("b1").set("degree", new String[]{"1"});
    model.geom("geom1").feature("b1")
         .set("p", new String[][]{{"0.00508", "0"}, {"-0.01334", "-0.011"}});
    model.geom("geom1").feature("b1").set("w", new String[]{"1", "1"});
    model.geom("geom1").feature("pt1").name("S1");
    model.geom("geom1").feature("pt1").setIndex("p", "OuterRadius", 0, 0);
    model.geom("geom1").feature("pt1").setIndex("p", "z_S1", 1, 0);
    model.geom("geom1").feature("pt2").name("S0");
    model.geom("geom1").feature("pt2").setIndex("p", "r_S0", 0, 0);
    model.geom("geom1").feature("pt2")
         .setIndex("p", "-InnerHeight/2-EndHeight", 1, 0);
    model.geom("geom1").feature("pt3").name("S5");
    model.geom("geom1").feature("pt3").setIndex("p", "r_S5", 0, 0);
    model.geom("geom1").feature("pt3")
         .setIndex("p", "InnerHeight/2+EndHeight", 1, 0);
    model.geom("geom1").feature("sq1").active(false);
    model.geom("geom1").feature("sq1")
         .set("pos", new String[]{"r_S5", "InnerHeight/2+EndHeight-1[mm]"});
    model.geom("geom1").feature("sq1").set("size", "1 [mm]");
    model.geom("geom1").run();

    model.view().create("view2", 3);
    model.view().create("view3", 3);
    model.view().create("view4", 3);
    model.view().create("view5", 3);
    model.view().create("view6", 3);

    model.material().create("mat4");
    model.material("mat4").propertyGroup("def").func()
         .create("eta", "Piecewise");
    model.material("mat4").propertyGroup("def").func()
         .create("Cp", "Piecewise");
    model.material("mat4").propertyGroup("def").func()
         .create("rho", "Analytic");
    model.material("mat4").propertyGroup("def").func()
         .create("k", "Piecewise");
    model.material("mat4").propertyGroup().create("idealGas", "Ideal gas");
    model.material("mat4").selection()
         .set(new int[]{4, 5, 6, 7, 8, 10, 21, 22, 25, 26, 27});
    model.material().create("mat2");
    model.material("mat2").propertyGroup()
         .create("Enu", "Young's modulus and Poisson's ratio");
    model.material("mat2").propertyGroup()
         .create("linzRes", "Linearized resistivity");
    model.material("mat2").propertyGroup()
         .create("CpCs", "Pressure-wave and shear-wave speeds");
    model.material("mat2").propertyGroup()
         .create("KG", "Bulk modulus and shear modulus");
    model.material("mat2").selection()
         .set(new int[]{9, 11, 18, 19, 20, 23, 24});
    model.material().create("mat3");
    model.material("mat3").propertyGroup()
         .create("comp1", "User-defined property group");
    model.material("mat3").propertyGroup()
         .create("CpCs", "Pressure-wave and shear-wave speeds");
    model.material("mat3").propertyGroup()
         .create("KG", "Bulk modulus and shear modulus");
    model.material("mat3").selection()
         .set(new int[]{1, 2, 3, 12, 13, 14, 15, 16, 17, 18, 19, 20, 28});
    model.material().create("mat5");
    model.material("mat5").propertyGroup("def").func()
         .create("eta", "Piecewise");
    model.material("mat5").propertyGroup("def").func()
         .create("Cp", "Piecewise");
    model.material("mat5").propertyGroup("def").func()
         .create("rho", "Analytic");
    model.material("mat5").propertyGroup("def").func()
         .create("k", "Piecewise");
    model.material("mat5").propertyGroup("def").func()
         .create("cs", "Analytic");
    model.material("mat5").propertyGroup().create("idealGas", "Ideal gas");
    model.material("mat5").selection().set(new int[]{1, 2});

    model.physics().create("astd", "TransientAcousticSolid", "geom1");
    model.physics("astd").field("pressure").field("p2");
    model.physics("astd").feature("dlemm1").selection()
         .set(new int[]{3, 9, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 23, 24, 28});
    model.physics("astd").feature("dlemm1").feature()
         .create("dmp1", "Damping", 2);
    model.physics("astd").feature()
         .create("tpam2", "TransientPressureAcousticsModel", 2);
    model.physics("astd").feature("tpam2").selection().set(new int[]{1, 2});
    model.physics("astd").feature().create("dlemm2", "LinearElasticModel", 2);
    model.physics("astd").feature("dlemm2").selection()
         .set(new int[]{9, 11, 23, 24});
    model.physics("astd").feature("dlemm2").feature()
         .create("dmp1", "Damping", 2);
    model.physics("astd").feature().create("pwr1", "PlaneWaveRadiation", 1);
    model.physics("astd").feature("pwr1").selection()
         .set(new int[]{2, 33, 85});
    model.physics("astd").feature().create("bndl1", "BoundaryLoad", 1);
    model.physics("astd").feature("bndl1").selection().set(new int[]{73, 75});
    model.physics("astd").feature().create("bndl2", "BoundaryLoad", 1);
    model.physics("astd").feature("bndl2").selection().set(new int[]{78});
    model.physics("astd").feature().create("bndl3", "BoundaryLoad", 1);
    model.physics("astd").feature("bndl3").selection()
         .set(new int[]{72, 74, 86, 87});
    model.physics("astd").feature().create("tel1", "ThinElasticLayer", 1);
    model.physics("astd").feature("tel1").selection().set(new int[]{79, 80});
    model.physics("astd").feature().create("tel2", "ThinElasticLayer", 1);
    model.physics("astd").feature("tel2").selection().set(new int[]{70});
    model.physics("astd").feature().create("tel3", "ThinElasticLayer", 1);
    model.physics("astd").feature("tel3").selection().set(new int[]{71});

    model.mesh().create("mesh1", "geom1");
    model.mesh("mesh1").feature().create("ftri1", "FreeTri");

    model.view("view1").axis().set("xmin", "-0.19092489778995514");
    model.view("view1").axis().set("ymin", "-0.16745392978191376");
    model.view("view1").axis().set("xmax", "0.3941250443458557");
    model.view("view1").axis().set("ymax", "0.16745392978191376");

    model.material("mat4").name("Nitrogen");
    model.material("mat4").propertyGroup("def").func("eta")
         .set("pieces", new String[][]{{"200.0", "1200.0", "1.77230303E-6+6.27427545E-8*T^1-3.47278555E-11*T^2+1.01243201E-14*T^3"}});
    model.material("mat4").propertyGroup("def").func("eta").set("arg", "T");
    model.material("mat4").propertyGroup("def").func("Cp")
         .set("pieces", new String[][]{{"200.0", "1200.0", "1088.22121-0.365941919*T^1+7.88715035E-4*T^2-3.749223E-7*T^3+3.17599068E-11*T^4"}});
    model.material("mat4").propertyGroup("def").func("Cp").set("arg", "T");
    model.material("mat4").propertyGroup("def").func("rho")
         .set("args", new String[]{"pA", "T"});
    model.material("mat4").propertyGroup("def").func("rho")
         .set("expr", "pA*0.02801/8.314/T");
    model.material("mat4").propertyGroup("def").func("rho")
         .set("dermethod", "manual");
    model.material("mat4").propertyGroup("def").func("rho")
         .set("plotargs", new String[][]{{"pA", "0", "1"}, {"T", "0", "1"}});
    model.material("mat4").propertyGroup("def").func("rho")
         .set("argders", new String[][]{{"pA", "d(pA*0.02801/8.314/T,pA)"}, {"T", "d(pA*0.02801/8.314/T,T)"}});
    model.material("mat4").propertyGroup("def").func("k")
         .set("pieces", new String[][]{{"200.0", "1200.0", "3.6969697E-4+9.74353924E-5*T^1-4.07587413E-8*T^2+7.68453768E-12*T^3"}});
    model.material("mat4").propertyGroup("def").func("k").set("arg", "T");
    model.material("mat4").propertyGroup("def")
         .set("dynamicviscosity", "eta(T[1/K])[Pa*s]");
    model.material("mat4").propertyGroup("def")
         .set("ratioofspecificheat", "1.4");
    model.material("mat4").propertyGroup("def").set("density", "rho_N2");
    model.material("mat4").propertyGroup("def")
         .set("thermalconductivity", new String[]{"k(T[1/K])[W/(m*K)]", "0", "0", "0", "k(T[1/K])[W/(m*K)]", "0", "0", "0", "k(T[1/K])[W/(m*K)]"});
    model.material("mat4").propertyGroup("def").set("soundspeed", "c_N2");
    model.material("mat4").propertyGroup("def").addInput("temperature");
    model.material("mat4").propertyGroup("def").addInput("pressure");
    model.material("mat4").propertyGroup("idealGas").set("Rs", "297");
    model.material("mat4").propertyGroup("idealGas")
         .set("heatcapacity", "1040");
    model.material("mat4").propertyGroup("idealGas")
         .set("ratioofspecificheat", "1.4");
    model.material("mat4").propertyGroup("idealGas")
         .set("molarmass", "28.0e-3");
    model.material("mat4").propertyGroup("idealGas").addInput("temperature");
    model.material("mat4").propertyGroup("idealGas").addInput("pressure");
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
    model.material("mat2").propertyGroup("def").set("density", "rho_Cu");
    model.material("mat2").propertyGroup("def")
         .set("thermalconductivity", new String[]{"400[W/(m*K)]", "0", "0", "0", "400[W/(m*K)]", "0", "0", "0", "400[W/(m*K)]"});
    model.material("mat2").propertyGroup("def").set("soundspeed", "4760");
    model.material("mat2").propertyGroup("Enu").set("youngsmodulus", "117e9");
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
    model.material("mat2").propertyGroup("CpCs").set("cp", "");
    model.material("mat2").propertyGroup("CpCs").set("cs", "");
    model.material("mat2").propertyGroup("CpCs").set("cp", "c_p_Cu");
    model.material("mat2").propertyGroup("CpCs").set("cs", "c_s_Cu");
    model.material("mat2").propertyGroup("KG").set("K", "");
    model.material("mat2").propertyGroup("KG").set("G", "");
    model.material("mat2").propertyGroup("KG").set("K", "K_Cu");
    model.material("mat2").propertyGroup("KG").set("G", "G_Cu");
    model.material("mat3").name("316 Stainless Steel");
    model.material("mat3").propertyGroup("def").set("density", "rho_SS");
    model.material("mat3").propertyGroup("def").set("soundspeed", "5790");
    model.material("mat3").propertyGroup("def")
         .set("relpermeability", new String[]{"1", "0", "0", "0", "1", "0", "0", "0", "1"});
    model.material("mat3").propertyGroup("def")
         .set("electricconductivity", new String[]{"1.42e6", "0", "0", "0", "1.42e6", "0", "0", "0", "1.42e6"});
    model.material("mat3").propertyGroup("def")
         .set("thermalexpansioncoefficient", new String[]{"1.6e-5", "0", "0", "0", "1.6e-5", "0", "0", "0", "1.6e-5"});
    model.material("mat3").propertyGroup("def").set("heatcapacity", "500");
    model.material("mat3").propertyGroup("def")
         .set("relpermittivity", new String[]{"1", "0", "0", "0", "1", "0", "0", "0", "1"});
    model.material("mat3").propertyGroup("def")
         .set("thermalconductivity", new String[]{"16.2", "0", "0", "0", "16.2", "0", "0", "0", "16.2"});
    model.material("mat3").propertyGroup("def")
         .set("youngsmodulus", "197.5e9");
    model.material("mat3").propertyGroup("def").set("poissonsratio", "0.30");
    model.material("mat3").propertyGroup("def").set("lossfactor", ".04");
    model.material("mat3").propertyGroup("CpCs").set("cp", "");
    model.material("mat3").propertyGroup("CpCs").set("cs", "");
    model.material("mat3").propertyGroup("CpCs").set("cp", "c_p_SS");
    model.material("mat3").propertyGroup("CpCs").set("cs", "c_s_SS");
    model.material("mat3").propertyGroup("KG").set("K", "");
    model.material("mat3").propertyGroup("KG").set("G", "");
    model.material("mat3").propertyGroup("KG").set("K", "K_SS");
    model.material("mat3").propertyGroup("KG").set("G", "G_SS");
    model.material("mat5").name("Air");
    model.material("mat5").propertyGroup("def").func("eta")
         .set("pieces", new String[][]{{"200.0", "1600.0", "-8.38278E-7+8.35717342E-8*T^1-7.69429583E-11*T^2+4.6437266E-14*T^3-1.06585607E-17*T^4"}});
    model.material("mat5").propertyGroup("def").func("eta").set("arg", "T");
    model.material("mat5").propertyGroup("def").func("Cp")
         .set("pieces", new String[][]{{"200.0", "1600.0", "1047.63657-0.372589265*T^1+9.45304214E-4*T^2-6.02409443E-7*T^3+1.2858961E-10*T^4"}});
    model.material("mat5").propertyGroup("def").func("Cp").set("arg", "T");
    model.material("mat5").propertyGroup("def").func("rho")
         .set("args", new String[]{"pA", "T"});
    model.material("mat5").propertyGroup("def").func("rho")
         .set("expr", "pA*0.02897/8.314/T");
    model.material("mat5").propertyGroup("def").func("rho")
         .set("dermethod", "manual");
    model.material("mat5").propertyGroup("def").func("rho")
         .set("plotargs", new String[][]{{"pA", "0", "1"}, {"T", "0", "1"}});
    model.material("mat5").propertyGroup("def").func("rho")
         .set("argders", new String[][]{{"pA", "d(pA*0.02897/8.314/T,pA)"}, {"T", "d(pA*0.02897/8.314/T,T)"}});
    model.material("mat5").propertyGroup("def").func("k")
         .set("pieces", new String[][]{{"200.0", "1600.0", "-0.00227583562+1.15480022E-4*T^1-7.90252856E-8*T^2+4.11702505E-11*T^3-7.43864331E-15*T^4"}});
    model.material("mat5").propertyGroup("def").func("k").set("arg", "T");
    model.material("mat5").propertyGroup("def").func("cs")
         .set("args", new String[]{"T"});
    model.material("mat5").propertyGroup("def").func("cs")
         .set("expr", "sqrt(1.4*287*T)");
    model.material("mat5").propertyGroup("def").func("cs")
         .set("dermethod", "manual");
    model.material("mat5").propertyGroup("def").func("cs")
         .set("plotargs", new String[][]{{"T", "0", "1"}});
    model.material("mat5").propertyGroup("def").func("cs")
         .set("argders", new String[][]{{"T", "d(sqrt(1.4*287*T),T)"}});
    model.material("mat5").propertyGroup("def")
         .set("dynamicviscosity", "eta(T[1/K])[Pa*s]");
    model.material("mat5").propertyGroup("def")
         .set("ratioofspecificheat", "1.4");
    model.material("mat5").propertyGroup("def")
         .set("electricconductivity", new String[]{"0[S/m]", "0", "0", "0", "0[S/m]", "0", "0", "0", "0[S/m]"});
    model.material("mat5").propertyGroup("def")
         .set("heatcapacity", "Cp(T[1/K])[J/(kg*K)]");
    model.material("mat5").propertyGroup("def")
         .set("density", "rho(pA[1/Pa],T[1/K])[kg/m^3]");
    model.material("mat5").propertyGroup("def")
         .set("thermalconductivity", new String[]{"k(T[1/K])[W/(m*K)]", "0", "0", "0", "k(T[1/K])[W/(m*K)]", "0", "0", "0", "k(T[1/K])[W/(m*K)]"});
    model.material("mat5").propertyGroup("def")
         .set("soundspeed", "cs(T[1/K])[m/s]");
    model.material("mat5").propertyGroup("def").addInput("temperature");
    model.material("mat5").propertyGroup("def").addInput("pressure");
    model.material("mat5").propertyGroup("idealGas").set("Rs", "286.9");
    model.material("mat5").propertyGroup("idealGas")
         .set("heatcapacity", "1.01e3");
    model.material("mat5").propertyGroup("idealGas")
         .set("ratioofspecificheat", "1.40");
    model.material("mat5").propertyGroup("idealGas")
         .set("molarmass", "28.97[g/mol]");
    model.material("mat5").propertyGroup("idealGas").addInput("temperature");
    model.material("mat5").propertyGroup("idealGas").addInput("pressure");

    model.physics("astd").feature("tpam1").set("FluidModel", "IdealGas");
    model.physics("astd").feature("tpam1").set("editModelInputs", "1");
    model.physics("astd").feature("tpam1")
         .set("minput_pressure", "300 [psi]");
    model.physics("astd").feature("tpam1").name("Pressurized Nitrogen Model");
    model.physics("astd").feature("dlemm1").set("IsotropicOption", "CpCs");
    model.physics("astd").feature("dlemm1").name("Stainless Steel Material");
    model.physics("astd").feature("dlemm1").feature("dmp1")
         .set("beta_dK", "1e-8");
    model.physics("astd").feature("tpam2").set("FluidModel", "IdealGas");
    model.physics("astd").feature("tpam2").set("editModelInputs", "1");
    model.physics("astd").feature("tpam2").set("minput_pressure", "1 [atm]");
    model.physics("astd").feature("tpam2").name("Air Acoustics Model");
    model.physics("astd").feature("dlemm2").set("IsotropicOption", "CpCs");
    model.physics("astd").feature("dlemm2").name("Copper Material");
    model.physics("astd").feature("dlemm2").feature("dmp1")
         .set("beta_dK", "5e-4");
    model.physics("astd").feature("bndl1")
         .set("FperArea", new String[][]{{"0"}, {"0"}, {"-z/abs(z)*RFEnvelope(t)*f_wall(r)"}});
    model.physics("astd").feature("bndl1").set("coordinateSystem", "sys1");
    model.physics("astd").feature("bndl1")
         .name("End Flange RF Hammer Envelope Load");
    model.physics("astd").feature("bndl2")
         .set("FperArea", new String[][]{{"0"}, {"0"}, {"-RFEnvelope(t)*f_circ(z)"}});
    model.physics("astd").feature("bndl2").set("coordinateSystem", "sys1");
    model.physics("astd").feature("bndl2")
         .name("Center Flange RF Hammer Envelope Load");
    model.physics("astd").feature("bndl3")
         .set("FperArea", new String[][]{{"0"}, {"0"}, {"RFEnvelope(t)*f_button(z)"}});
    model.physics("astd").feature("bndl3").set("coordinateSystem", "sys1");
    model.physics("astd").feature("bndl3")
         .name("Button RF Hammer Envelope Load");
    model.physics("astd").feature("tel1").set("SpringType", "kTot");
    model.physics("astd").feature("tel1")
         .set("kTot", new String[][]{{"k_tot_flange_r"}, {"0"}, {"k_tot_flange_z"}});
    model.physics("astd").feature("tel1")
         .name("Thin Elastic Layer (Flanges)");
    model.physics("astd").feature("tel2").set("SpringType", "kTot");
    model.physics("astd").feature("tel2")
         .set("kTot", new String[][]{{"k_tot_button_r_up"}, {"0"}, {"k_tot_button_z_up"}});
    model.physics("astd").feature("tel2")
         .name("Thin Elastic Layer (Upstream Button)");
    model.physics("astd").feature("tel3").set("SpringType", "kTot");
    model.physics("astd").feature("tel3")
         .set("kTot", new String[][]{{"k_tot_button_r_down"}, {"0"}, {"k_tot_button_z_down"}});
    model.physics("astd").feature("tel3")
         .name("Thin Elastic Layer (Downstream Button)");

    model.mesh("mesh1").feature("size").set("hauto", 3);
    model.mesh("mesh1").run();

    model.study().create("std1");
    model.study("std1").feature().create("param", "Parametric");
    model.study("std1").feature().create("time", "Transient");
    model.study().create("std2");
    model.study("std2").feature().create("eig", "Eigenfrequency");
    model.study("std2").feature().create("frmod", "Frequencymodal");

    model.sol().create("sol1");
    model.sol("sol1").study("std1");
    model.sol("sol1").attach("std1");
    model.sol("sol1").feature().create("st1", "StudyStep");
    model.sol("sol1").feature().create("v1", "Variables");
    model.sol("sol1").feature().create("t1", "Time");
    model.sol("sol1").feature("t1").feature().create("fc1", "FullyCoupled");
    model.sol("sol1").feature("t1").feature().remove("fcDef");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std2").feature("frmod").set("initstudyhide", "on");
    model.study("std2").feature("frmod").set("initsolhide", "on");
    model.study("std2").feature("frmod").set("notstudyhide", "on");
    model.study("std2").feature("frmod").set("notsolhide", "on");

    model.sol().create("sol2");
    model.sol("sol2").study("std1");
    model.sol("sol2").feature().create("su1", "StoreSolution");
    model.sol("sol2").feature().create("su2", "StoreSolution");
    model.sol("sol2").feature().create("su3", "StoreSolution");
    model.sol("sol2").feature().create("su4", "StoreSolution");
    model.sol("sol2").feature().create("su5", "StoreSolution");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std2").feature("frmod").set("initstudyhide", "on");
    model.study("std2").feature("frmod").set("initsolhide", "on");
    model.study("std2").feature("frmod").set("notstudyhide", "on");
    model.study("std2").feature("frmod").set("notsolhide", "on");

    model.sol("sol3").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std2").feature("frmod").set("initstudyhide", "on");
    model.study("std2").feature("frmod").set("initsolhide", "on");
    model.study("std2").feature("frmod").set("notstudyhide", "on");
    model.study("std2").feature("frmod").set("notsolhide", "on");

    model.sol("sol4").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std2").feature("frmod").set("initstudyhide", "on");
    model.study("std2").feature("frmod").set("initsolhide", "on");
    model.study("std2").feature("frmod").set("notstudyhide", "on");
    model.study("std2").feature("frmod").set("notsolhide", "on");

    model.sol("sol5").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std2").feature("frmod").set("initstudyhide", "on");
    model.study("std2").feature("frmod").set("initsolhide", "on");
    model.study("std2").feature("frmod").set("notstudyhide", "on");
    model.study("std2").feature("frmod").set("notsolhide", "on");

    model.sol("sol6").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std2").feature("frmod").set("initstudyhide", "on");
    model.study("std2").feature("frmod").set("initsolhide", "on");
    model.study("std2").feature("frmod").set("notstudyhide", "on");
    model.study("std2").feature("frmod").set("notsolhide", "on");

    model.sol("sol7").study("std1");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std2").feature("frmod").set("initstudyhide", "on");
    model.study("std2").feature("frmod").set("initsolhide", "on");
    model.study("std2").feature("frmod").set("notstudyhide", "on");
    model.study("std2").feature("frmod").set("notsolhide", "on");

    model.sol().create("sol8");
    model.sol("sol8").study("std2");
    model.sol("sol8").attach("std2");
    model.sol("sol8").feature().create("st1", "StudyStep");
    model.sol("sol8").feature().create("v1", "Variables");
    model.sol("sol8").feature().create("e1", "Eigenvalue");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std2").feature("frmod").set("initstudyhide", "on");
    model.study("std2").feature("frmod").set("initsolhide", "on");
    model.study("std2").feature("frmod").set("notstudyhide", "on");
    model.study("std2").feature("frmod").set("notsolhide", "on");

    model.sol().create("sol9");
    model.sol("sol9").study("std2");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std2").feature("frmod").set("initstudyhide", "on");
    model.study("std2").feature("frmod").set("initsolhide", "on");
    model.study("std2").feature("frmod").set("notstudyhide", "on");
    model.study("std2").feature("frmod").set("notsolhide", "on");

    model.sol().create("sol10");
    model.sol("sol10").study("std2");

    model.study("std1").feature("time").set("initstudyhide", "on");
    model.study("std1").feature("time").set("initsolhide", "on");
    model.study("std1").feature("time").set("notstudyhide", "on");
    model.study("std1").feature("time").set("notsolhide", "on");
    model.study("std2").feature("eig").set("initstudyhide", "on");
    model.study("std2").feature("eig").set("initsolhide", "on");
    model.study("std2").feature("eig").set("notstudyhide", "on");
    model.study("std2").feature("eig").set("notsolhide", "on");
    model.study("std2").feature("frmod").set("initstudyhide", "on");
    model.study("std2").feature("frmod").set("initsolhide", "on");
    model.study("std2").feature("frmod").set("notstudyhide", "on");
    model.study("std2").feature("frmod").set("notsolhide", "on");

    model.batch().create("p1", "Parametric");
    model.batch("p1").feature().create("so1", "Solutionseq");
    model.batch("p1").study("std1");

    model.result().dataset().create("cpt1", "CutPoint2D");
    model.result().dataset().create("cpt2", "CutPoint2D");
    model.result().dataset().create("rev1", "Revolve2D");
    model.result().dataset().create("rev2", "Revolve2D");
    model.result().dataset().create("gp1_ds1", "Function1D");
    model.result().dataset().create("pw1_ds1", "Function1D");
    model.result().dataset().create("pw1_ds2", "Function1D");
    model.result().dataset().create("pw1_ds3", "Function1D");
    model.result().dataset().create("cln1", "CutLine2D");
    model.result().dataset().create("cpt4", "CutPoint2D");
    model.result().dataset().create("cpt3", "CutPoint2D");
    model.result().dataset().create("cpt5", "CutPoint2D");
    model.result().dataset().create("av1", "Average");
    model.result().dataset().create("cpt6", "CutPoint2D");
    model.result().dataset().create("cpt7", "CutPoint2D");
    model.result().dataset().create("cpt8", "CutPoint2D");
    model.result().dataset().create("cpt9", "CutPoint2D");
    model.result().dataset().create("rev3", "Revolve2D");
    model.result().dataset("cpt1").set("data", "dset2");
    model.result().dataset("rev1").set("data", "dset2");
    model.result().dataset("rev2").set("data", "dset2");
    model.result().dataset("cln1").set("data", "dset2");
    model.result().dataset("cpt4").set("data", "dset2");
    model.result().dataset("cpt3").set("data", "dset2");
    model.result().dataset("cpt5").set("data", "dset2");
    model.result().dataset("av1").set("data", "cln1");
    model.result().dataset("cpt6").set("data", "dset2");
    model.result().dataset("cpt7").set("data", "dset2");
    model.result().dataset("cpt8").set("data", "dset2");
    model.result().dataset("cpt9").set("data", "dset2");
    model.result().dataset("dset3").set("solution", "sol8");
    model.result().dataset("dset4").set("solution", "sol9");
    model.result().dataset("rev3").set("data", "dset3");
    model.result().dataset("dset5").set("solution", "sol10");
    model.result().dataset().remove("dset6");
    model.result().dataset().remove("dset7");
    model.result().dataset().remove("dset8");
    model.result().dataset().remove("dset9");
    model.result().dataset().remove("dset10");
    model.result().create("pg1", "PlotGroup1D");
    model.result().create("pg3", "PlotGroup1D");
    model.result().create("pg10", "PlotGroup1D");
    model.result().create("pg2", "PlotGroup2D");
    model.result().create("pg6", "PlotGroup2D");
    model.result().create("pg7", "PlotGroup2D");
    model.result().create("pg8", "PlotGroup3D");
    model.result().create("pg9", "PlotGroup3D");
    model.result().create("pg11", "PlotGroup2D");
    model.result().create("pg12", "PlotGroup2D");
    model.result().create("pg13", "PlotGroup3D");
    model.result().create("pg14", "PlotGroup3D");
    model.result("pg1").feature().create("ptgr1", "PointGraph");
    model.result("pg1").feature().create("ptgr2", "PointGraph");
    model.result("pg3").feature().create("ptgr8", "PointGraph");
    model.result("pg3").feature().create("ptgr9", "PointGraph");
    model.result("pg3").feature().create("ptgr11", "PointGraph");
    model.result("pg3").feature().create("ptgr5", "PointGraph");
    model.result("pg3").feature().create("ptgr6", "PointGraph");
    model.result("pg3").feature().create("ptgr10", "PointGraph");
    model.result("pg3").feature("ptgr5").selection().set(new int[]{56});
    model.result("pg3").feature("ptgr10").selection().set(new int[]{50, 51});
    model.result("pg10").feature().create("ptgr1", "PointGraph");
    model.result("pg10").feature().create("ptgr2", "PointGraph");
    model.result("pg10").feature().create("ptgr3", "PointGraph");
    model.result("pg2").feature().create("con1", "Contour");
    model.result("pg2").feature().create("con2", "Contour");
    model.result("pg2").feature().create("con3", "Contour");
    model.result("pg2").feature().create("arws1", "ArrowSurface");
    model.result("pg2").feature().create("surf2", "Surface");
    model.result("pg2").feature().create("surf1", "Surface");
    model.result("pg2").feature().create("surf3", "Surface");
    model.result("pg2").feature().create("arws2", "ArrowSurface");
    model.result("pg6").feature().create("surf1", "Surface");
    model.result("pg6").feature("surf1").feature().create("def", "Deform");
    model.result("pg7").feature().create("surf1", "Surface");
    model.result("pg7").feature("surf1").feature().create("hght", "Height");
    model.result("pg8").feature().create("surf1", "Surface");
    model.result("pg8").feature("surf1").feature().create("def", "Deform");
    model.result("pg9").feature().create("surf1", "Surface");
    model.result("pg11").feature().create("surf1", "Surface");
    model.result("pg11").feature("surf1").feature().create("def", "Deform");
    model.result("pg12").feature().create("surf1", "Surface");
    model.result("pg12").feature("surf1").feature().create("hght", "Height");
    model.result("pg13").feature().create("surf1", "Surface");
    model.result("pg13").feature("surf1").feature().create("def", "Deform");
    model.result("pg14").feature().create("surf1", "Surface");
    model.result().export().create("plot1", "Plot");

    model.study("std1").feature("param")
         .set("pname", new String[]{"k_tot_flange_r", "k_tot_flange_z"});
    model.study("std1").feature("param")
         .set("plistarr", new String[]{"5e9", "1e9"});
    model.study("std1").feature("param").set("sweeptype", "filled");
    model.study("std1").feature("time")
         .set("tlist", "range(0,2[us],300[us])");
    model.study("std2").feature("eig").set("shift", "21000");
    model.study("std2").feature("frmod").active(false);
    model.study("std2").feature("frmod")
         .set("plist", "range(12500,100,13500)");

    model.sol("sol1").attach("std1");
    model.sol("sol1").feature("st1")
         .name("Compile Equations: Time Dependent");
    model.sol("sol1").feature("st1").set("studystep", "time");
    model.sol("sol1").feature("v1").set("control", "time");
    model.sol("sol1").feature("t1").set("tlist", "range(0,2[us],300[us])");
    model.sol("sol1").feature("t1").set("timemethod", "genalpha");
    model.sol("sol1").feature("t1").set("timestepgenalpha", "2e-6");
    model.sol("sol1").feature("t1").set("tstepsgenalpha", "manual");
    model.sol("sol1").feature("t1").set("control", "time");
    model.sol("sol1").feature("t1").set("rhoinf", "0");
    model.sol("sol2").name("Parametric 2");
    model.sol("sol8").attach("std2");
    model.sol("sol8").feature("st1")
         .name("Compile Equations: Eigenfrequency");
    model.sol("sol8").feature("st1").set("study", "std2");
    model.sol("sol8").feature("v1").set("control", "eig");
    model.sol("sol8").feature("e1").set("control", "eig");
    model.sol("sol8").feature("e1").set("eigref", "100");
    model.sol("sol8").feature("e1").set("transform", "eigenfrequency");
    model.sol("sol8").feature("e1").set("shift", "21000");
    model.sol("sol9").name("Store Solution 9");
    model.sol("sol10").name("Store Solution 10");

    model.batch("p1").set("control", "param");
    model.batch("p1").set("err", true);
    model.batch("p1").set("sweeptype", "filled");
    model.batch("p1").set("plistarr", new String[]{"5e9", "1e9"});
    model.batch("p1")
         .set("pname", new String[]{"k_tot_flange_r", "k_tot_flange_z"});
    model.batch("p1").set("control", "param");
    model.batch("p1").feature("so1").set("psol", "sol2");
    model.batch("p1").feature("so1")
         .set("param", new String[]{"\"k_tot_flange_r\",\"1e10\",\"k_tot_flange_z\",\"1e8\"", "\"k_tot_flange_r\",\"1e10\",\"k_tot_flange_z\",\"10e8\"", "\"k_tot_flange_r\",\"1e10\",\"k_tot_flange_z\",\"1e10\"", "\"k_tot_flange_r\",\"1e10\",\"k_tot_flange_z\",\"1e11\"", "\"k_tot_flange_r\",\"1e10\",\"k_tot_flange_z\",\"10e11\""});
    model.batch("p1").feature("so1").set("seq", "sol1");
    model.batch("p1").attach("std1");
    model.batch("p1").run();

    model.result().dataset("cpt1").name("Inner Radius");
    model.result().dataset("cpt1").set("bndsnap", true);
    model.result().dataset("cpt1").set("pointx", "InnerRadius");
    model.result().dataset("cpt1").set("pointy", "0");
    model.result().dataset("cpt2").name("Inner Wall");
    model.result().dataset("cpt2").set("bndsnap", true);
    model.result().dataset("cpt2").set("pointx", "ButtonRadius+1[mm]");
    model.result().dataset("cpt2").set("pointy", "-InnerHeight/2");
    model.result().dataset("rev2").set("startangle", "-90");
    model.result().dataset("rev2").set("hasspacevars", "on");
    model.result().dataset("rev2").set("revangle", "225");
    model.result().dataset("gp1_ds1").set("par1", "t");
    model.result().dataset("gp1_ds1").set("parmax1", "2.4999999999999998E-6");
    model.result().dataset("gp1_ds1").set("function", "gp1");
    model.result().dataset("gp1_ds1").set("parmin1", "-5.000000000000001E-7");
    model.result().dataset("pw1_ds1").set("par1", "t");
    model.result().dataset("pw1_ds1").set("parmax1", "2.9999999999999997E-5");
    model.result().dataset("pw1_ds1").set("function", "all");
    model.result().dataset("pw1_ds2").set("par1", "t");
    model.result().dataset("pw1_ds2").set("parmax1", "0");
    model.result().dataset("pw1_ds2").set("function", "all");
    model.result().dataset("pw1_ds2").set("parmin1", "-3.0E-6");
    model.result().dataset("pw1_ds3").set("par1", "t");
    model.result().dataset("pw1_ds3").set("parmax1", "3.2999999999999996E-5");
    model.result().dataset("pw1_ds3").set("function", "all");
    model.result().dataset("pw1_ds3").set("parmin1", "2.9999999999999997E-5");
    model.result().dataset("cln1").name("S0 Region");
    model.result().dataset("cln1")
         .set("genpoints", new String[][]{{"0.90*InnerRadius", "-InnerHeight/2-EndHeight"}, {"0.99*InnerRadius", "-InnerHeight/2-EndHeight"}});
    model.result().dataset("cln1").set("bndsnap", true);
    model.result().dataset("cpt4").name("S0");
    model.result().dataset("cpt4").set("bndsnap", true);
    model.result().dataset("cpt4").set("pointx", "1.0*InnerRadius");
    model.result().dataset("cpt4").set("pointy", "-InnerHeight/2-EndHeight");
    model.result().dataset("cpt3").name("S1");
    model.result().dataset("cpt3").set("bndsnap", true);
    model.result().dataset("cpt3").set("pointx", "15.24 [cm]");
    model.result().dataset("cpt3").set("pointy", "0 [cm]");
    model.result().dataset("cpt5").name("S5");
    model.result().dataset("cpt5").set("bndsnap", true);
    model.result().dataset("cpt5").set("pointx", "0.9*InnerRadius");
    model.result().dataset("cpt5").set("pointy", "InnerHeight/2+EndHeight");
    model.result().dataset("cpt6").name("Downstream Wobble");
    model.result().dataset("cpt6").set("bndsnap", true);
    model.result().dataset("cpt6").set("pointx", "2.2 [cm]");
    model.result().dataset("cpt6").set("pointy", "InnerHeight/2-0.15 [cm]");
    model.result().dataset("cpt7").name("Downstream Slide");
    model.result().dataset("cpt7").set("bndsnap", true);
    model.result().dataset("cpt7").set("pointx", "0.5 [cm]");
    model.result().dataset("cpt7").set("pointy", "InnerHeight/2-0.15 [cm]");
    model.result().dataset("cpt8").name("Upstream Wobble");
    model.result().dataset("cpt8").set("bndsnap", true);
    model.result().dataset("cpt8").set("pointx", "2.2 [cm]");
    model.result().dataset("cpt8").set("pointy", "-InnerHeight/2+0.15 [cm]");
    model.result().dataset("cpt9").name("Upstream Slide");
    model.result().dataset("cpt9").set("bndsnap", true);
    model.result().dataset("cpt9").set("pointx", "0.5 [cm]");
    model.result().dataset("cpt9").set("pointy", "-InnerHeight/2+0.15 [cm]");
    model.result().dataset("rev3").set("startangle", "-90");
    model.result().dataset("rev3").set("hasspacevars", "on");
    model.result().dataset("rev3").set("revangle", "225");
    model.result("pg1").name("1D Gas Plots");
    model.result("pg1").set("ylabel", "Total acoustic pressure field (Pa)");
    model.result("pg1").set("manualgrid", "on");
    model.result("pg1").set("xlabel", "Time (s)");
    model.result("pg1").set("xspacing", "2.0E-6");
    model.result("pg1").set("yspacing", "1000000");
    model.result("pg1").set("xlabelactive", false);
    model.result("pg1").set("ylabelactive", false);
    model.result("pg1").feature("ptgr1").set("data", "cpt1");
    model.result("pg1").feature("ptgr1").set("expr", "astd.p_t");
    model.result("pg1").feature("ptgr1")
         .set("descr", "Total acoustic pressure field");
    model.result("pg1").feature("ptgr2").active(false);
    model.result("pg1").feature("ptgr2").set("data", "cpt2");
    model.result("pg1").feature("ptgr2").set("expr", "actd.p_t");
    model.result("pg1").feature("ptgr2")
         .set("descr", "Total acoustic pressure field");
    model.result("pg1").feature("ptgr2").set("unit", "Pa");
    model.result("pg3").name("1D Solid Plots");
    model.result("pg3").set("data", "dset2");
    model.result("pg3").set("ylabel", "-astd.u_ttR (m/s<sup>2</sup>)");
    model.result("pg3").set("manualgrid", "on");
    model.result("pg3").set("xlabel", "Time (s)");
    model.result("pg3").set("xspacing", "2.0E-6");
    model.result("pg3").set("yspacing", "1.0E-12");
    model.result("pg3").set("xlabelactive", false);
    model.result("pg3").set("ylabelactive", false);
    model.result("pg3").feature("ptgr8").active(false);
    model.result("pg3").feature("ptgr8").name("S0 Z Acceleration");
    model.result("pg3").feature("ptgr8").set("data", "cpt4");
    model.result("pg3").feature("ptgr8").set("expr", "astd.u_ttZ");
    model.result("pg3").feature("ptgr8").set("unit", "m/s^2");
    model.result("pg3").feature("ptgr8")
         .set("descr", "Acceleration, Z component");
    model.result("pg3").feature("ptgr9").active(false);
    model.result("pg3").feature("ptgr9").name("S5 Z Acceleration");
    model.result("pg3").feature("ptgr9").set("data", "cpt5");
    model.result("pg3").feature("ptgr9").set("expr", "-astd.u_ttZ");
    model.result("pg3").feature("ptgr9").set("unit", "m/s^2");
    model.result("pg3").feature("ptgr9").set("descr", "-astd.u_ttZ");
    model.result("pg3").feature("ptgr11").name("S1 Z Acceleration");
    model.result("pg3").feature("ptgr11").set("data", "cpt3");
    model.result("pg3").feature("ptgr11").set("expr", "-astd.u_ttR");
    model.result("pg3").feature("ptgr11").set("unit", "m/s^2");
    model.result("pg3").feature("ptgr11").set("descr", "-astd.u_ttR");
    model.result("pg3").feature("ptgr5").active(false);
    model.result("pg3").feature("ptgr5").name("S1 Pressure");
    model.result("pg3").feature("ptgr5").set("expr", "astd.pm");
    model.result("pg3").feature("ptgr5").set("unit", "N/m^2");
    model.result("pg3").feature("ptgr6").active(false);
    model.result("pg3").feature("ptgr6").name("S0 Pressure");
    model.result("pg3").feature("ptgr6").set("data", "cpt4");
    model.result("pg3").feature("ptgr6").set("expr", "-astd.pm*1e-3");
    model.result("pg3").feature("ptgr6").set("unit", "N/m^2");
    model.result("pg3").feature("ptgr6").set("descr", "-astd.pm*1e-3");
    model.result("pg3").feature("ptgr10").active(false);
    model.result("pg3").feature("ptgr10").name("S5 Pressure");
    model.result("pg3").feature("ptgr10").set("expr", "astd.pm");
    model.result("pg3").feature("ptgr10").set("unit", "N/m^2");
    model.result("pg10").name("Wobble and Slide");
    model.result("pg10").set("xlabel", "Time (s)");
    model.result("pg10").set("xlabelactive", false);
    model.result("pg10").feature("ptgr1").name("DownstreamWobble");
    model.result("pg10").feature("ptgr1").set("data", "cpt6");
    model.result("pg10").feature("ptgr1").set("expr", "astd.u_ttZ");
    model.result("pg10").feature("ptgr1").set("unit", "m/s^2");
    model.result("pg10").feature("ptgr1")
         .set("descr", "Acceleration, Z component");
    model.result("pg10").feature("ptgr2").active(false);
    model.result("pg10").feature("ptgr2").name("Slide");
    model.result("pg10").feature("ptgr2").set("data", "cpt6");
    model.result("pg10").feature("ptgr2").set("expr", "astd.u_ttR");
    model.result("pg10").feature("ptgr2").set("unit", "m/s^2");
    model.result("pg10").feature("ptgr2")
         .set("descr", "Acceleration, R component");
    model.result("pg10").feature("ptgr3").name("Upstream Wobble");
    model.result("pg10").feature("ptgr3").set("data", "cpt8");
    model.result("pg10").feature("ptgr3").set("expr", "-astd.u_ttZ");
    model.result("pg10").feature("ptgr3").set("unit", "m/s^2");
    model.result("pg10").feature("ptgr3").set("descr", "-astd.u_ttZ");
    model.result("pg2").set("data", "dset2");
    model.result("pg2").feature("con1").active(false);
    model.result("pg2").feature("con1").name("Pressure Contours");
    model.result("pg2").feature("con1").set("expr", "astd.p_t");
    model.result("pg2").feature("con1").set("levelmethod", "levels");
    model.result("pg2").feature("con1")
         .set("levels", "range(-90000000,180000000/99,90000000)");
    model.result("pg2").feature("con1")
         .set("descr", "Total acoustic pressure field");
    model.result("pg2").feature("con2").active(false);
    model.result("pg2").feature("con2").name("R Displacement Contours");
    model.result("pg2").feature("con2").set("expr", "-u");
    model.result("pg2").feature("con2").set("levelmethod", "levels");
    model.result("pg2").feature("con2")
         .set("levels", "range(-8.6e-5,8.6e-5/99,8.6e-5)");
    model.result("pg2").feature("con2").set("unit", "m");
    model.result("pg2").feature("con2").set("descr", "-u");
    model.result("pg2").feature("con3").active(false);
    model.result("pg2").feature("con3").name("Z Displacement Contours");
    model.result("pg2").feature("con3").set("expr", "w");
    model.result("pg2").feature("con3").set("levelmethod", "levels");
    model.result("pg2").feature("con3")
         .set("levels", "range(-8.6e-5,8.6e-5/99,8.6e-5)");
    model.result("pg2").feature("con3").set("unit", "m");
    model.result("pg2").feature("con3")
         .set("descr", "Displacement field, Z component");
    model.result("pg2").feature("arws1").name("Solid Displacement Field");
    model.result("pg2").feature("arws1").set("scaleactive", true);
    model.result("pg2").feature("arws1").set("scale", "6749036.356520102");
    model.result("pg2").feature("arws1").set("xnumber", "50");
    model.result("pg2").feature("arws1").set("ynumber", "50");
    model.result("pg2").feature("arws1").set("arrowlength", "logarithmic");
    model.result("pg2").feature("surf2").active(false);
    model.result("pg2").feature("surf2").name("Solid Displacement Magnitude");
    model.result("pg2").feature("surf2").set("descr", "z/abs(z)*w");
    model.result("pg2").feature("surf2")
         .set("rangecolormax", "2.14170508630802E-10");
    model.result("pg2").feature("surf2").set("expr", "z/abs(z)*w");
    model.result("pg2").feature("surf2").set("rangecoloractive", "on");
    model.result("pg2").feature("surf2").set("rangecolormin", "-6.0E-10");
    model.result("pg2").feature("surf2").set("unit", "m");
    model.result("pg2").feature("surf1").name("Gas Pressure");
    model.result("pg2").feature("surf1")
         .set("descr", "Total acoustic pressure field");
    model.result("pg2").feature("surf1")
         .set("rangecolormax", "6.6406618005699");
    model.result("pg2").feature("surf1").set("expr", "astd.p_t");
    model.result("pg2").feature("surf1").set("rangecoloractive", "on");
    model.result("pg2").feature("surf1")
         .set("rangecolormin", "-0.399085640423202");
    model.result("pg2").feature("surf3").active(false);
    model.result("pg2").feature("surf3").name("Solid Z Acceleration");
    model.result("pg2").feature("surf3")
         .set("descr", "Total acceleration, R component");
    model.result("pg2").feature("surf3")
         .set("rangecolormax", "7.50571109883315");
    model.result("pg2").feature("surf3").set("expr", "astd.accR");
    model.result("pg2").feature("surf3").set("rangecoloractive", "on");
    model.result("pg2").feature("surf3")
         .set("rangecolormin", "-3.59156010836403");
    model.result("pg2").feature("surf3").set("unit", "m/s^2");
    model.result("pg2").feature("arws2").active(false);
    model.result("pg2").feature("arws2").name("Solid Acceleration Field");
    model.result("pg2").feature("arws2")
         .set("expr", new String[]{"astd.accR", "astd.accZ"});
    model.result("pg2").feature("arws2")
         .set("descr", "Total acceleration (Material)");
    model.result("pg2").feature("arws2").set("scaleactive", true);
    model.result("pg2").feature("arws2").set("scale", "4.90239186246834E-4");
    model.result("pg2").feature("arws2").set("xnumber", "50");
    model.result("pg2").feature("arws2").set("ynumber", "50");
    model.result("pg2").feature("arws2").set("arrowlength", "logarithmic");
    model.result("pg6").name("Displacement (astd)");
    model.result("pg6").set("data", "dset2");
    model.result("pg6").feature("surf1").set("descr", "Total displacement");
    model.result("pg6").feature("surf1").set("expr", "astd.disp");
    model.result("pg6").feature("surf1").set("unit", "m");
    model.result("pg7").name("Acoustic Pressure (astd)");
    model.result("pg7").set("data", "dset2");
    model.result("pg7").feature("surf1")
         .set("descr", "Total acoustic pressure field");
    model.result("pg7").feature("surf1").set("expr", "astd.p_t");
    model.result("pg7").feature("surf1").set("colortable", "WaveLight");
    model.result("pg7").feature("surf1").set("colortablesym", true);
    model.result("pg8").name("Displacement, 3D (astd)");
    model.result("pg8").set("data", "rev2");
    model.result("pg8").feature("surf1").set("expr", "astd.disp");
    model.result("pg8").feature("surf1").set("unit", "m");
    model.result("pg8").feature("surf1").set("descr", "Total displacement");
    model.result("pg8").feature("surf1").feature("def")
         .set("descr", "Displacement field (Material)");
    model.result("pg8").feature("surf1").feature("def")
         .set("descractive", true);
    model.result("pg8").feature("surf1").feature("def")
         .set("expr", new String[]{"cos(rev2phi)*u", "sin(rev2phi)*u", "w"});
    model.result("pg9").name("Acoustic Pressure, 3D (astd)");
    model.result("pg9").set("data", "rev2");
    model.result("pg9").feature("surf1").set("colortablesym", true);
    model.result("pg9").feature("surf1").set("expr", "astd.p_t");
    model.result("pg9").feature("surf1")
         .set("descr", "Total acoustic pressure field");
    model.result("pg9").feature("surf1").set("colortable", "WaveLight");
    model.result("pg11").name("Displacement (astd) 1");
    model.result("pg11").set("data", "dset3");
    model.result("pg11").feature("surf1").set("descr", "Total displacement");
    model.result("pg11").feature("surf1").set("expr", "astd.disp");
    model.result("pg11").feature("surf1").set("unit", "m");
    model.result("pg11").feature("surf1").feature("def")
         .set("scale", "1.2356870743880562E10");
    model.result("pg11").feature("surf1").feature("def")
         .set("scaleactive", false);
    model.result("pg12").name("Acoustic Pressure (astd) 1");
    model.result("pg12").set("data", "dset3");
    model.result("pg12").feature("surf1")
         .set("descr", "Total acoustic pressure field");
    model.result("pg12").feature("surf1").set("expr", "astd.p_t");
    model.result("pg12").feature("surf1").set("colortable", "WaveLight");
    model.result("pg12").feature("surf1").set("colortablesym", true);
    model.result("pg12").feature("surf1").feature("hght")
         .set("scale", "0.013807165510310974");
    model.result("pg12").feature("surf1").feature("hght")
         .set("scaleactive", false);
    model.result("pg13").name("Displacement, 3D (astd) 1");
    model.result("pg13").set("data", "rev3");
    model.result("pg13").feature("surf1").set("expr", "astd.disp");
    model.result("pg13").feature("surf1").set("unit", "m");
    model.result("pg13").feature("surf1").set("descr", "Total displacement");
    model.result("pg13").feature("surf1").feature("def")
         .set("scale", "3.151859277077384E9");
    model.result("pg13").feature("surf1").feature("def")
         .set("descr", "Displacement field (Material)");
    model.result("pg13").feature("surf1").feature("def")
         .set("descractive", true);
    model.result("pg13").feature("surf1").feature("def")
         .set("expr", new String[]{"cos(rev3phi)*u", "sin(rev3phi)*u", "w"});
    model.result("pg13").feature("surf1").feature("def")
         .set("scaleactive", false);
    model.result("pg14").name("Acoustic Pressure, 3D (astd) 1");
    model.result("pg14").set("data", "rev3");
    model.result("pg14").feature("surf1").set("colortablesym", true);
    model.result("pg14").feature("surf1").set("expr", "astd.p_t");
    model.result("pg14").feature("surf1")
         .set("descr", "Total acoustic pressure field");
    model.result("pg14").feature("surf1").set("colortable", "WaveLight");
    model.result().export("plot1")
         .set("filename", "C:\\Users\\plane\\Desktop\\Data\\COMSOL\\HPRF\\RF Hammer Phase Tuning\\s1_up_1e8_down_1e9_flange_1e10_1e12.csv");
    model.result().export("plot1").set("plotgroup", "pg3");
    model.result().export("plot1").set("plot", "ptgr11");

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

    return model;
  }

}

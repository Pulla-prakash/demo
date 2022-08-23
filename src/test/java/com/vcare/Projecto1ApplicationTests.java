/*
 * package com.vcare;
 * 
 * import static org.junit.Assert.assertNotSame; import static
 * org.junit.Assert.assertNull; import static org.junit.Assert.assertSame;
 * import static org.junit.Assert.assertTrue; import static
 * org.junit.jupiter.api.Assertions.assertArrayEquals; import static
 * org.junit.jupiter.api.Assertions.assertEquals; import static
 * org.junit.jupiter.api.Assertions.assertFalse; import static
 * org.junit.jupiter.api.Assertions.assertNotEquals; import static
 * org.junit.jupiter.api.Assertions.assertNotNull; import static
 * org.junit.jupiter.api.Assertions.assertThrows; import static
 * org.mockito.Mockito.when;
 * 
 * import java.util.ArrayList; import java.util.List; import java.util.Scanner;
 * 
 * import org.junit.Before; import org.junit.jupiter.api.Test; import
 * org.junit.runner.RunWith; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.boot.test.context.SpringBootTest; import
 * org.springframework.boot.test.mock.mockito.MockBean; import
 * org.springframework.test.context.junit4.SpringRunner;
 * 
 * import com.vcare.beans.Appointment; import com.vcare.beans.Department; import
 * com.vcare.beans.Doctor; import com.vcare.beans.HospitalBranch; import
 * com.vcare.beans.Patients; import com.vcare.repository.AppointmentRepository;
 * import com.vcare.repository.DepartmentRepository; import
 * com.vcare.service.AppointmentService; import
 * com.vcare.service.DepartmentService;
 * 
 * @RunWith(SpringRunner.class)
 * 
 * @SpringBootTest class Projecto1ApplicationTests {
 * 
 * @Autowired DepartmentService departmentService;
 * 
 * @MockBean DepartmentRepository departmentRepository;
 * 
 * @MockBean private AppointmentRepository appointmentRepository;
 * 
 * @Autowired AppointmentService appointmentService;
 * 
 * 
 * private ArrayList<Department> getDepartment() {
 * 
 * Department objDepart =new Department();
 * 
 * objDepart.setDepartmentId(123); objDepart.setDepartmentName("account");
 * objDepart.setDescription("hello"); objDepart.setIsactive('Y'); HospitalBranch
 * obj=new HospitalBranch(); obj.setHospitalBranchId(128);
 * objDepart.setHospitalBranch(obj);
 * 
 * ArrayList <Department> objPart= new ArrayList<Department>();
 * 
 * objPart.add(objDepart);
 * 
 * return objPart; }
 * 
 * 
 * @Before public void before() {
 * 
 * System.out.println("start the test cases"); }
 * 
 * 
 * @Test
 * 
 * public void getAllDepartments() { List<Department> department=
 * getDepartment(); when(departmentRepository.findAll()).thenReturn(department);
 * List<Department> objDepartment =departmentService.getAllDepartments();
 * assertEquals(department.size(),objDepartment.size());
 * assertTrue(department.size() == objDepartment.size());
 * 
 * }
 * 
 * 
 * 
 * @Test public void getByName() {
 * 
 * List<Department> department= getDepartment();
 * when(departmentRepository.findByName("account")).thenReturn(department);
 * assertEquals(department.size(),department.size());
 * 
 * }
 * 
 * 
 * 
 * 
 * @Test public void deleteByDepartment() {
 * 
 * List<Department> department= getDepartment();
 * 
 * //willDoNothing().given(departmentService.deleteDepartmentById(123));
 * //when(departmentService.deleteDepartmentById(123).thenReturn(null));
 * 
 * }
 * 
 * 
 * @Test public void updateDepartment() {
 * 
 * List<Department> department= getDepartment(); assertNotNull(department);
 * department.get(0).setDepartmentId(22); //
 * when(departmentService.getDepartmentById(22)).thenReturn(department.get(0));
 * when(departmentService.getDepartmentById(department.get(0).getDepartmentId())
 * ).thenReturn(department.get(0));
 * 
 * Department
 * obj=departmentService.getDepartmentById(department.get(0).getDepartmentId());
 * 
 * assertNotNull(obj); }
 * 
 * 
 * private ArrayList<Appointment> getAppointment() { Appointment appointment=new
 * Appointment(); Doctor d=new Doctor(); d.setDoctorId(200);
 * appointment.setAppointmentId(100); appointment.setConsultantType("Direct");
 * appointment.setDoctor(d); appointment.setCreatedBy("Ajith");
 * appointment.setDate("22-09-2022"); appointment.setHospitalBranchId(300);
 * appointment.setIsactive('N'); appointment.setLink("link"); Patients o=new
 * Patients(); o.setPatientId(400); appointment.setPatient(o);
 * appointment.setPatientPurpose("purpose");
 * appointment.setPaymentStatus("NOTPAID"); appointment.setSlot("14:40");
 * appointment.setUpdateBy("chintu"); appointment.setAppointmentValidity(9);
 * 
 * Appointment appointmenttwo=new Appointment(); Doctor doc=new Doctor();
 * doc.setDoctorId(199); appointmenttwo.setAppointmentId(99);
 * appointmenttwo.setConsultantType("Video"); appointmenttwo.setDoctor(doc);
 * appointmenttwo.setCreatedBy("Abhi"); appointmenttwo.setDate("22-09-2022");
 * appointmenttwo.setHospitalBranchId(299); appointmenttwo.setIsactive('N');
 * appointmenttwo.setLink("link"); Patients pa=new Patients();
 * pa.setPatientId(399); appointmenttwo.setPatient(pa);
 * appointmenttwo.setPatientPurpose("purpose");
 * appointmenttwo.setPaymentStatus("PAID"); appointmenttwo.setSlot("15:40");
 * appointmenttwo.setUpdateBy("Ram"); appointmenttwo.setAppointmentValidity(9);
 * 
 * ArrayList<Appointment> appointmentList=new ArrayList<Appointment>();
 * appointmentList.add(appointmenttwo); appointmentList.add(appointment); return
 * appointmentList; }
 * 
 * @Test public void testFindAll() { List<Appointment>
 * appointment=getAppointment(); when(appointmentRepository.findAll()).
 * thenReturn((List)appointment); Appointment
 * appone=appointmentService.getAppointmentById(appointment.get(0).
 * getAppointmentId()); List<Appointment>
 * app=appointmentService.getAllAppointment();
 * 
 * //assert methods assertNotNull(app); assertEquals(appointment,app);
 * assertEquals(appointment.size(),app.size()); assertSame(appointment, app);
 * 
 * String a[]={"a","b"}; String b[]={"a","b"}; assertArrayEquals(a, b);
 * 
 * assertFalse(false); assertNotEquals(appointment, appone); Appointment
 * nul=null; assertNull(nul); assertSame(appointment, app);
 * assertNotSame(appointment, appone); //assertTrue(false);//error
 * assertTrue(true); }
 * 
 * @Test public void testFindById() { List<Appointment>
 * appointment=getAppointment();
 * when(appointmentService.getAppointmentById(appointment.get(0).
 * getAppointmentId())).thenReturn(appointment.get(0)); Appointment
 * app=appointmentService.getAppointmentById(appointment.get(0).getAppointmentId
 * ()); //List<Appointment> app=appointmentService.getAllAppointment();
 * assertEquals(app.getAppointmentId(),99); }
 * 
 * @Test public void testDelete() { List<Appointment>
 * appointment=getAppointment();
 * when(appointmentService.getAppointmentById(appointment.get(0).
 * getAppointmentId())).thenReturn(null); Appointment
 * nul=appointmentService.getAppointmentById(appointment.get(0).getAppointmentId
 * ()); System.err.println("deleted value"+ nul); assertNull(nul); }
 * 
 * @Test public void testUpdate() { List<Appointment>
 * appointment=getAppointment(); Scanner sc=new Scanner(System.in);
 * System.err.print("Enter new AppointmentId= "); double appid=sc.nextDouble();
 * appointment.get(0).setAppointmentId((long)appid);
 * when(appointmentService.getAppointmentById(appointment.get(0).
 * getAppointmentId())).thenReturn(appointment.get(0)); Appointment
 * nul=appointmentService.getAppointmentById(appointment.get(0).getAppointmentId
 * ()); assertNotNull(nul); }
 * 
 * 
 * 
 * 
 * 
 * }
 */
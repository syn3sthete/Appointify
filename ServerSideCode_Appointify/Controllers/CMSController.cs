using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using ClientManagementSystem;
using ClinicManagementSystem.Service;
using ClinicManagementSystem.Service.Entity;


namespace ClientManagementSystem.Controllers
{
    public class CMSController : ApiController
    {
        CMSDataService objService=new ClinicManagementSystem.Service.CMSDataService();
        [HttpPost] 
         public BaseReturnType<long> Register (EPatient patient)
        
        {
           return objService.Register(patient);
        }

        [HttpPost]
        public BaseReturnType<long> ValidateUser(EPatient patient)
        {
            return objService.ValidateUser(patient);
        }

         [HttpPost]
        public BaseReturnType<List<ETimeSlot>> GetAvailableTimeSlot(ETimeSlotRequest timesSlotReq)
        
        {
            return objService.GetAvailableTimeSlots(timesSlotReq);
        }

         [HttpPost]
         public BaseReturnType<EMakeAppointMentResponse> MakeAppointment(EMakeAppointMent makeappointment)
         {
             return objService.MakeAppointment(makeappointment);
         }

         [HttpPost]
         public BaseReturnType<bool> CancelAppointment(ECancelAppointment reqCancelAppointment)
         {
             return objService.CancelAppointment(reqCancelAppointment);
         }
       

         [HttpPost]
         public BaseReturnType<List<EGetAppointmentHistoryResponse>> GetAppointmentHistory(EPatient reqGetAppointmentHistory)
         {
             return objService.GetAppointmentHistory(reqGetAppointmentHistory);
         }
  
    }
}

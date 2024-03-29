USE [DEV_ClinicDB]
GO
/****** Object:  StoredProcedure [dbo].[Usp_GetAppointmentHistory]    Script Date: 06/01/2014 04:19:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER Procedure [dbo].[Usp_GetAppointmentHistory] --100
@PatientID bigint
AS
Begin
	select RA.PatientID,RA.AppointmentNo,RA.DoctorID,RA.BookedSlotID,RA.FirstName,RA.MiddleName,RA.LastName,
	RA.Places,IsCompleted,RA.DateBooked,RA.AppointmentDate,DSM.StartTime,DSM.EndTime
	from RegisteredAppointment RA
	inner join Bookedslots BS on RA.AppointmentNo=BS.AppointmentNo
	inner join doctorslotmaster DSM on DSM.DoctorSlotID=BS.DoctorSlotID
	Where RA.PatientID=@PatientID order by RA.DateBooked
End




declare @PatientID bigint = 101;

select RA.PatientID,RA.AppointmentNo,RA.DoctorID,RA.BookedSlotID,RA.FirstName,RA.MiddleName,RA.LastName,
	RA.Places,IsCompleted,RA.DateBooked,RA.AppointmentDate,DSM.StartTime,DSM.EndTime
	from RegisteredAppointment RA
	inner join Bookedslots BS on RA.AppointmentNo=BS.AppointmentNo
	inner join doctorslotmaster DSM on DSM.DoctorSlotID=BS.DoctorSlotID
	Where RA.PatientID=@PatientID order by RA.DateBooked
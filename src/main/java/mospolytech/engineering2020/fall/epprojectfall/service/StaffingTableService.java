package mospolytech.engineering2020.fall.epprojectfall.service;

import mospolytech.engineering2020.fall.epprojectfall.domain.StaffingTable;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.Page;
import mospolytech.engineering2020.fall.epprojectfall.domain.paging.PagingRequest;


public interface StaffingTableService extends CrudService<StaffingTable, Long> {
    Page<StaffingTable> getStaffingTables(PagingRequest pagingRequest);
    void deleteAll(Iterable<StaffingTable> departments);
}
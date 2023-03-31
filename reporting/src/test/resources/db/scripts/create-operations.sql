--1) operation types
insert into operation_types(id, name, is_expense) values(1, 'Пополнение', false);
insert into operation_types(id, name, is_expense) values(2, 'Перевод пользователю', true);
insert into operation_types(id, name, is_expense) values(3, 'Вывод средств', true);

--2) operation statuses
insert into operation_statuses(id, name) values(1, 'Создана');
insert into operation_statuses(id, name) values(2, 'Обрабатывается');
insert into operation_statuses(id, name) values(3, 'Исполнена');
insert into operation_statuses(id, name) values(4, 'Отменена');

--3) bank accounts
insert into bank_accounts(id, user_id, is_enabled, create_date, update_date) values(1, 1, true, current_timestamp, current_timestamp);
insert into bank_accounts(id, user_id, is_enabled, create_date, update_date) values(2, 2, false, current_timestamp, current_timestamp);
insert into bank_accounts(id, user_id, is_enabled, create_date, update_date) values(3, 2, true, current_timestamp, current_timestamp);
insert into bank_accounts(id, user_id, is_enabled, create_date, update_date) values(4, 3, true, current_timestamp, current_timestamp);
insert into bank_accounts(id, user_id, is_enabled, create_date, update_date) values(5, 4, true, current_timestamp, current_timestamp);

--4) operations
insert into operations(id, account_source_id, account_target_id, operation_type_id, date, update_date, amount)
    values(1, 1, null, 1, current_timestamp, current_timestamp, 1000);

insert into operations(id, account_source_id, account_target_id, operation_type_id, date, update_date, amount)
    values(2, 1, 3, 2, current_timestamp, current_timestamp, 200);

insert into operations(id, account_source_id, account_target_id, operation_type_id, date, update_date, amount)
    values(3, 3, null, 3, current_timestamp, current_timestamp, 100);

insert into operations(id, account_source_id, account_target_id, operation_type_id, date, update_date, amount)
    values(4, 3, 4, 2, current_timestamp, current_timestamp, 50);

--5) account details
insert into account_details(id, bank_account_id, actual_date, amount)
    values(1, 1, current_timestamp, 800);
insert into account_details(id, bank_account_id, actual_date, amount)
    values(2, 3, current_timestamp, 50);
insert into account_details(id, bank_account_id, actual_date, amount)
    values(3, 4, current_timestamp, 50);

--6) operation status history
insert into operation_status_history(id, operation_id, status_id, date)
    values(1, 1, 1, '2023-02-22 23:03:31-04:00:00');
insert into operation_status_history(id, operation_id, status_id, date)
    values(2, 1, 2, '2023-02-22 23:03:31-04:00:00');
insert into operation_status_history(id, operation_id, status_id, date)
    values(3, 1, 3, '2023-02-22 23:03:31-04:00:00');
insert into operation_status_history(id, operation_id, status_id, date)
    values(4, 2, 1, '2023-02-22 23:03:36-04:00:00');
insert into operation_status_history(id, operation_id, status_id, date)
    values(5, 2, 2, '2023-02-22 23:03:36-04:00:00');
insert into operation_status_history(id, operation_id, status_id, date)
    values(6, 2, 3, '2023-02-22 23:03:36-04:00:00');
insert into operation_status_history(id, operation_id, status_id, date)
    values(7, 3, 1, '2023-02-22 23:03:52-04:00:00');
insert into operation_status_history(id, operation_id, status_id, date)
    values(8, 3, 2, '2023-02-22 23:03:52-04:00:00');
insert into operation_status_history(id, operation_id, status_id, date)
    values(9, 3, 3, '2023-02-22 23:03:52-04:00:00');
insert into operation_status_history(id, operation_id, status_id, date)
    values(10, 4, 1, '2023-02-28 17:29:13-04:00:00');
insert into operation_status_history(id, operation_id, status_id, date)
    values(11, 4, 2, '2023-02-28 17:29:14-04:00:00');
insert into operation_status_history(id, operation_id, status_id, date)
    values(12, 4, 3, '2023-02-28 17:29:16-04:00:00');
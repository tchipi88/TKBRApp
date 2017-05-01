(function() {
    'use strict';

    angular
        .module('app')
        .controller('AuthorityDialogController', AuthorityDialogController);

    AuthorityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Authority','AccessGroup'];

    function AuthorityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Authority,AccessGroup) {
        var vm = this;

        vm.authority = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.accessGroups=AccessGroup.query();
        //vm.authority.name='MAGASINIER';
      

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;          
            if (vm.authority.name !== null) {
                Authority.update(vm.authority, onSaveSuccess, onSaveError);
            } else {
                Authority.save(vm.authority, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('tkbrApp:authorityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        
        
         function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

    }
})();
